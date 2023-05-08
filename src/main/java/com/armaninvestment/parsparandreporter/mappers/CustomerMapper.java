package com.armaninvestment.parsparandreporter.mappers;


import com.armaninvestment.parsparandreporter.dtos.CustomerDto;
import com.armaninvestment.parsparandreporter.entities.Customer;


public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);

    Customer updateCustomerFromCustomerDto(CustomerDto customerDto, Customer customer);

    default void linkPayments(Customer customer) {
        customer.getPayments().forEach(payment -> payment.setCustomer(customer));
    }
}
