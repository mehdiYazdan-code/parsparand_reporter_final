package com.armaninvestment.parsparandreporter.controllers;


import com.armaninvestment.parsparandreporter.mappers.PaymentMapper;
import com.armaninvestment.parsparandreporter.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/payments")
public class PaymentController {
    private final PaymentRepository repository;
    public final PaymentMapper mapper;
    @Autowired
    public PaymentController(PaymentRepository repository, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
