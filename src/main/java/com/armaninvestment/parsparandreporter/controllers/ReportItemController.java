package com.armaninvestment.parsparandreporter.controllers;


import com.armaninvestment.parsparandreporter.dtos.CompanyReportDTO;
import com.armaninvestment.parsparandreporter.mappers.ReportItemMapper;
import com.armaninvestment.parsparandreporter.repositories.ReportItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reportItems")
public class ReportItemController {
    private final ReportItemRepository repository;
    private final ReportItemMapper mapper;
    @Autowired
    public ReportItemController(ReportItemRepository repository, ReportItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @GetMapping("/{year}/{month}")
    public List<CompanyReportDTO> getCompanyReportsByMonthAndYear(
            @PathVariable Integer year,
            @PathVariable Integer month
    ) {
        return repository.getCompanyReportsByMonthAndYear(month, year);
    }
}
