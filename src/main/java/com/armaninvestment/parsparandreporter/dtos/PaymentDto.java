package com.armaninvestment.parsparandreporter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
    private Long id;
    private String description;
    private LocalDate date;
    private Double amount;
}