package com.armaninvestment.parsparandreporter.repositories;


import com.armaninvestment.parsparandreporter.dtos.CompanyReportDTO;
import com.armaninvestment.parsparandreporter.entities.ReportItem;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<ReportItem, Long> {
    @Query(value = """
            SELECT new com.example.dto.CompanyReportDTO(
                       c.id,
                       c.name,
                      SUM(ri.quantity) OVER quantity,
                      ROUND(AVG(ri.unit_price))  AS avg_unit_price,
                                                                                                                              
                       SUM(ri.quantity * ri.unit_price) AS amount,

                       (SELECT SUM(ri2.quantity)
                        FROM report_item ri2
                                 JOIN report r2 ON r2.id = ri2.report_id
                        WHERE ri2.customer_id = c.id
                          AND get_persian_year(gregorian_to_persian(r2.report_date)) = :year
                          AND get_persian_month(gregorian_to_persian(r2.report_date)) <= :month) AS cumulativeQuantity
                FROM Customer c
                         JOIN ReportItem ri ON c.id = ri.customer_id
                         JOIN Report r ON r.id = ri.report_id
                WHERE get_persian_year(gregorian_to_persian(r.report_date)) = :year
                  AND get_persian_month(gregorian_to_persian(r.report_date)) = :month
                GROUP BY c.id, c.name""")
    List<CompanyReportDTO> getCompanyReportsByMonthAndYear(
            @Param("month") Integer month,
            @Param("year") Integer year
    );





}