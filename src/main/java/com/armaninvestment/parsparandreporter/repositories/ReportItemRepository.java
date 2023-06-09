package com.armaninvestment.parsparandreporter.repositories;


import com.armaninvestment.parsparandreporter.dtos.CompanyReportDTO;
import com.armaninvestment.parsparandreporter.entities.Report;
import com.armaninvestment.parsparandreporter.entities.ReportItem;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<ReportItem, Long> {

}