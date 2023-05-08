package com.armaninvestment.parsparandreporter.mappers;

import com.armaninvestment.parsparandreporter.dtos.ReportDto;
import com.armaninvestment.parsparandreporter.entities.Report;


public interface ReportMapper {
    Report toEntity(ReportDto reportDto);

    ReportDto toDto(Report report);

    Report updateEntityFromDto(ReportDto reportDto, Report report);

    default void linkReportItems(Report report) {
        report.getReportItems().forEach(reportItem -> reportItem.setReport(report));
    }
}
