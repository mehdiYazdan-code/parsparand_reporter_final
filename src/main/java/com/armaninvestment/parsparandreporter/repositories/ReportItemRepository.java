package com.armaninvestment.parsparandreporter.repositories;



import com.armaninvestment.parsparandreporter.entities.ReportItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<ReportItem, Long> {

}