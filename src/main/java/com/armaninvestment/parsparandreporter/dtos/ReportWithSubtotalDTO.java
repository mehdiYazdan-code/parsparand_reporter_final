package com.armaninvestment.parsparandreporter.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportWithSubtotalDTO {
    private Long id;
    private LocalDate date;
    private Long totalCount;
    private Long totalAmount;
}
