create function get_monthly_report_by_year_and_month(year integer, month integer)
    returns TABLE(id bigint, name character varying, quantity bigint, avg_unit_price numeric, amount numeric, cumulative_quantity bigint)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT c.id,
               c.name,
               SUM(ri.quantity) AS quantity,
               ROUND(AVG(ri.unit_price),0) AS avg_unit_price,
               SUM(ri.quantity * ri.unit_price) AS amount,
               (
                   SELECT SUM(ri2.quantity)
                   FROM report_item ri2
                            JOIN report r2 ON r2.id = ri2.report_id
                   WHERE ri2.customer_id = c.id
                     AND get_persian_year(gregorian_to_persian(r2.report_date)) = year
                     AND get_persian_month(gregorian_to_persian(r2.report_date)) <= month
               ) AS cumulative_quantity
        FROM customer c
                 JOIN report_item ri ON c.id = ri.customer_id
                 JOIN report r ON r.id = ri.report_id
        WHERE get_persian_year(gregorian_to_persian(r.report_date)) = year
          AND get_persian_month(gregorian_to_persian(r.report_date)) = month
        GROUP BY c.id, c.name;
END;
$$;

alter function get_monthly_report_by_year_and_month(integer, integer) owner to postgres;

