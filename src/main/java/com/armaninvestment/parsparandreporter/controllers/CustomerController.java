package com.armaninvestment.parsparandreporter.controllers;

import com.armaninvestment.parsparandreporter.dtos.*;
import com.armaninvestment.parsparandreporter.entities.Customer;
import com.armaninvestment.parsparandreporter.mappers.CustomerMapper;
import com.armaninvestment.parsparandreporter.mappers.CustomerSelectMapper;
import com.armaninvestment.parsparandreporter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/customers")
public class CustomerController {
    private  final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final CustomerSelectMapper selectMapper;

    @Autowired
    public CustomerController(CustomerRepository repository,
                              CustomerMapper mapper,
                              CustomerSelectMapper selectMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.selectMapper = selectMapper;
    }
    @GetMapping(path = {"/",""})
    public ResponseEntity<List<CustomerSelectDto>> findAll(){
       return ResponseEntity.ok(repository.findAll()
               .stream()
               .map(selectMapper::toDto).collect(Collectors.toList()));
    }
    @GetMapping("/{year}/monthly-sales")
    public ResponseEntity<List<MonthlySales>> findByPersianYearAndGroupedByMonth(@PathVariable("year") Short year) {
        List<Object[]> salesData = repository.findMonthlySalesByPersianYear(year);
        List<MonthlySales> monthlySalesList = new ArrayList<>();
        for (Object[] obj : salesData) {
            MonthlySales monthlySales = new MonthlySales();
            monthlySales.setMonth((Short) obj[0]);
            monthlySales.setMonthName((String) obj[1]);
            monthlySales.setTotalAmount((BigDecimal) obj[2]);
            monthlySales.setTotalQuantity((Long) obj[3]) ;
            monthlySalesList.add(monthlySales);
        }
        return ResponseEntity.ok(monthlySalesList);
    }
    @GetMapping("/{year}/monthly-payments")
    public ResponseEntity<List<MonthlyPayment>> findPaymentsByPersianYearAndGroupedByMonth(@PathVariable("year") Short year) {
        List<Object[]> paymentsData = repository.findMonthlyPaymentsByPersianYear(year);
        List<MonthlyPayment> monthlyPayments = new ArrayList<>();
        for (Object[] obj : paymentsData) {
            MonthlyPayment monthlyPayment = new MonthlyPayment();
            monthlyPayment.setMonth((Short) obj[0]);
            monthlyPayment.setMonthName((String) obj[1]);
            monthlyPayment.setTotalAmount((Double) obj[2]);
            monthlyPayments.add(monthlyPayment);
        }
        return ResponseEntity.ok(monthlyPayments);
    }
    @GetMapping("/{customerId}/{year}/monthly-sales")
    public ResponseEntity<List<MonthlySales>> findByCustomerAndPersianYearAndGroupedByMonth(
            @PathVariable BigInteger customerId, @PathVariable("year") Short year) {
        List<Object[]> salesData = repository.findMonthlySalesByCustomerAndPersianYear(customerId, year);
        List<MonthlySales> monthlySalesList = new ArrayList<>();
        for (Object[] obj : salesData) {
            MonthlySales monthlySales = new MonthlySales();
            monthlySales.setMonth((Short) obj[0]);
            monthlySales.setMonthName((String) obj[1]);
            monthlySales.setTotalAmount((BigDecimal) obj[2]);
            monthlySales.setTotalQuantity((Long) obj[3]);
            monthlySalesList.add(monthlySales);
        }

        return ResponseEntity.ok(monthlySalesList);
    }
    @GetMapping("/{customerId}/{year}/monthly-payments")
    public ResponseEntity<List<MonthlyPayment>> findPaymentsByCustomerAndPersianYearAndGroupedByMonth(
            @PathVariable BigInteger customerId, @PathVariable("year") Short year) {
        List<Object[]> paymentsData = repository.findMonthlyPaymentsByCustomerAndPersianYear(customerId, year);
        List<MonthlyPayment> monthlyPaymentList = new ArrayList<>();
        for (Object[] obj : paymentsData) {
            MonthlyPayment monthlyPayments = new MonthlyPayment();
            monthlyPayments.setMonth((Short) obj[0]);
            monthlyPayments.setMonthName((String) obj[1]);
            monthlyPayments.setTotalAmount((Double) obj[2]);
            monthlyPaymentList.add(monthlyPayments);
        }
        return ResponseEntity.ok(monthlyPaymentList);
    }
    @GetMapping("/{customerId}/{year}/monthly_report")
    public ResponseEntity<List<MonthlyReport>> getCustomerSummary(
            @PathVariable Integer customerId, @PathVariable Integer year) {

        List<Object[]> report = repository.getMonthlyReport(customerId, year);
        List<MonthlyReport> reportList = new ArrayList<>();
        for (Object[] obj : report) {
            MonthlyReport monthlyReport = new MonthlyReport();
            monthlyReport.setId((Long) obj[0]);
            monthlyReport.setName((String) obj[1]);
            monthlyReport.setTotalAmount((BigDecimal) obj[2]);
            monthlyReport.setTotalCount((Long) obj[3]);
            monthlyReport.setAvgPrice((BigDecimal) obj[4]);
            reportList.add(monthlyReport);
        }

        return ResponseEntity.ok(reportList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") Long id){
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            return ResponseEntity.ok(mapper.customerToCustomerDto(customer));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(path = {"/",""})
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = mapper.customerDtoToCustomer(customerDto);
        Customer save = repository.save(customer);
        return ResponseEntity.ok(mapper.customerToCustomerDto(save));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto){
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            Customer customerFromCustomerDto = mapper.updateCustomerFromCustomerDto(customerDto, customer);
            Customer update = repository.save(customerFromCustomerDto);
            return ResponseEntity.ok(mapper.customerToCustomerDto(update));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        try {
            Optional<Customer> optionalCustomer = repository.findById(id);
            if (optionalCustomer.isPresent()){
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
