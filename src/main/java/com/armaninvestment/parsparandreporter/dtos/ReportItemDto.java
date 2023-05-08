package com.armaninvestment.parsparandreporter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportItemDto implements Serializable {
    private Long id;
    private Long inventory_paper;
    private String productCode;
    private Long unitPrice;
    private Integer quantity;
    private Long customerId;
}