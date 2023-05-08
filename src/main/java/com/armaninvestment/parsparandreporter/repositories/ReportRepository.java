package com.armaninvestment.parsparandreporter.repositories;



import com.armaninvestment.parsparandreporter.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findByDateBetweenOrderByDateDesc(LocalDate fromDate, LocalDate toDate, Pageable paging);
    Page<Report> findByDateAfterOrderByDateDesc(LocalDate fromDate, Pageable paging);
    Page<Report> findByDateBeforeOrderByDateDesc(LocalDate toDate, Pageable paging);
}