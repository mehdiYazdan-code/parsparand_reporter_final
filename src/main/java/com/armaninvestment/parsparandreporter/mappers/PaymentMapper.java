package com.armaninvestment.parsparandreporter.mappers;


import com.armaninvestment.parsparandreporter.dtos.PaymentDto;
import com.armaninvestment.parsparandreporter.entities.Payment;



public interface PaymentMapper {

    Payment paymentDtoToPayment(PaymentDto paymentDto);

    PaymentDto paymentToPaymentDto(Payment payment);

    Payment updatePaymentFromPaymentDto(PaymentDto paymentDto, Payment payment);
}
