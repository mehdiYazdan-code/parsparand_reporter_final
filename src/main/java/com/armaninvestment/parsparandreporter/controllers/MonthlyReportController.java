package com.armaninvestment.parsparandreporter.controllers;

import com.armaninvestment.parsparandreporter.dtos.CompanyReportDTO;
import com.armaninvestment.parsparandreporter.repositories.MonthlyReportByYearAndMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/monthly-report")
public class MonthlyReportController {


    private final MonthlyReportByYearAndMonthRepository monthlyReportRepo;
    @Autowired
    public MonthlyReportController(MonthlyReportByYearAndMonthRepository monthlyReportRepo) {
        this.monthlyReportRepo = monthlyReportRepo;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<CompanyReportDTO>> getMonthlyReport(@PathVariable int year, @PathVariable int month) {
        List<CompanyReportDTO> report = monthlyReportRepo.getReport(year, month);
        return ResponseEntity.ok(report);
    }
}

