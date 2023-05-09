package com.armaninvestment.parsparandreporter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyReportDTO {
    private Long id;
    private String name;
    private Integer currentMonthQuantity;
    private Integer cumulativeQuantity;
    private Double unitPriceAvg;
    private Double currentMonthSales;
    private String reportDate;

}

