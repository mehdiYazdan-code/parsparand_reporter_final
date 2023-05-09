package com.armaninvestment.parsparandreporter.repositories;


import com.armaninvestment.parsparandreporter.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.name like concat('%', :name, '%')")
    Page<Customer> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM get_sales_by_customer_and_year_group_by_month(cast(:customerId as bigint), cast(:year as smallint))")
    List<Object[]> findMonthlySalesByCustomerAndPersianYear(BigInteger customerId, Short year);

    @Query(nativeQuery = true, value = "SELECT * FROM get_sales_by_year_group_by_month(cast(:year as smallint))")
    List<Object[]> findMonthlySalesByPersianYear(Short year);

    @Query(nativeQuery = true, value = "SELECT * FROM get_payments_by_customer_and_year_group_by_month(cast(:customerId as bigint), cast(:year as smallint))")
    List<Object[]> findMonthlyPaymentsByCustomerAndPersianYear(BigInteger customerId, Short year);

    @Query(nativeQuery = true, value = "SELECT * FROM get_payments_by_year_group_by_month(cast(:year as smallint))")
    List<Object[]> findMonthlyPaymentsByPersianYear(Short year);

    @Query(nativeQuery = true, value = "SELECT monthly_sales_by_month_and_year(cast(:customerId as integer), cast(:year as integer))")
    List<Object[]> getMonthlyReport(Integer customerId, Integer year);

}