package com.armaninvestment.parsparandreporter.controllers;



import com.armaninvestment.parsparandreporter.dtos.ReportDto;
import com.armaninvestment.parsparandreporter.dtos.ReportWithSubtotalDTO;
import com.armaninvestment.parsparandreporter.entities.Report;
import com.armaninvestment.parsparandreporter.entities.ReportItem;
import com.armaninvestment.parsparandreporter.mappers.ReportMapper;
import com.armaninvestment.parsparandreporter.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reports")
public class ReportController {
    private final ReportRepository repository;
    private final ReportMapper mapper;
    @Autowired
    public ReportController(ReportRepository repository, ReportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @GetMapping(path="/")
    public ResponseEntity<Map<String, Object>> getReportsWithSubtotals(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate endDate
    ) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Report> pagedResult;

        if (startDate != null && endDate != null) {
            pagedResult = repository.findByDateBetweenOrderByDateDesc(startDate, endDate, paging);
        } else if (startDate != null) {
            pagedResult = repository.findByDateAfterOrderByDateDesc(startDate, paging);
        } else if (endDate != null) {
            pagedResult = repository.findByDateBeforeOrderByDateDesc(endDate, paging);
        } else {
            pagedResult = repository.findAll(paging);
        }

        List<ReportWithSubtotalDTO> reportDTOs = pagedResult.getContent().stream().map(report -> {
            ReportWithSubtotalDTO reportDTO = new ReportWithSubtotalDTO();
            reportDTO.setId(report.getId());
            reportDTO.setDate(report.getDate());


            List<ReportItem> reportItems = report.getReportItems();
            long totalCount = 0;
            long totalAmount = 0;

            for (ReportItem reportItem : reportItems) {
                totalCount += reportItem.getQuantity();
                totalAmount += reportItem.getQuantity() * reportItem.getUnitPrice();
            }

            reportDTO.setTotalCount(totalCount);
            reportDTO.setTotalAmount(totalAmount);

            return reportDTO;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("reports", reportDTOs);
        response.put("totalPages", pagedResult.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReportDto> findById(@PathVariable("id") Long id){
        Optional<Report> optionalReport = repository.findById(id);
        if (optionalReport.isPresent()){
            Report report = optionalReport.get();
            return ResponseEntity.ok(mapper.toDto(report));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(path = {"/",""})
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto){
        Report report = mapper.toEntity(reportDto);
        Report save = repository.save(report);
        return ResponseEntity.ok(mapper.toDto(save));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<ReportDto> updateReport(@PathVariable("id") Long id, @RequestBody ReportDto reportDto){
        Optional<Report> optionalReport = repository.findById(id);
        if (optionalReport.isPresent()){
            Report report = optionalReport.get();
            Report reportFromReportDto = mapper.updateEntityFromDto(reportDto, report);
            Report update = repository.save(reportFromReportDto);
            return ResponseEntity.ok(mapper.toDto(update));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") Long id){
        try {
            Optional<Report> optionalReport = repository.findById(id);
            if (optionalReport.isPresent()){
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch (HttpServerErrorException.InternalServerError error){
            return ResponseEntity.internalServerError().build();
        }
    }
}
