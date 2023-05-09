create function monthly_sales_by_month_and_year(month_number integer, year integer)
    returns TABLE(customerid bigint, name character varying, total_amount numeric, total_count bigint, avg_price numeric)
    language plpgsql
as
$$
DECLARE
    customerId bigint;
BEGIN
    RETURN QUERY SELECT c.id, c.name, sum(ri.unit_price * ri.quantity) as total_amount, sum(ri.quantity) as total_count, round(avg(ri.unit_price)) as avg_price
                 FROM customer c
                          JOIN report_item ri ON c.id = ri.customer_id
                          JOIN report r ON r.id = ri.report_id
                 WHERE get_persian_year(gregorian_to_persian(r.report_date)) = year AND get_persian_month(gregorian_to_persian(r.report_date)) = month_number
                 GROUP BY c.id, c.name;
END;
$$;

alter function monthly_sales_by_month_and_year(integer, integer) owner to postgres;

