package com.armaninvestment.parsparandreporter.mappers;

import com.armaninvestment.parsparandreporter.dtos.PaymentDto;
import com.armaninvestment.parsparandreporter.entities.Payment;
import org.springframework.stereotype.Component;

import static com.armaninvestment.parsparandreporter.mappers.CustomerMapperImpl.getPayment;
import static com.armaninvestment.parsparandreporter.mappers.CustomerMapperImpl.getPaymentDto;

@Component
public class PaymentMapperImpl implements PaymentMapper{
    public PaymentMapperImpl() {
    }

    public Payment paymentDtoToPayment(PaymentDto paymentDto) {
        return getPayment(paymentDto);
    }

    public PaymentDto paymentToPaymentDto(Payment payment) {
        return getPaymentDto(payment);
    }

    public Payment updatePaymentFromPaymentDto(PaymentDto paymentDto, Payment payment) {
        if (paymentDto != null) {
            if (paymentDto.getId() != null) {
                payment.setId(paymentDto.getId());
            }

            if (paymentDto.getDescription() != null) {
                payment.setDescription(paymentDto.getDescription());
            }

            if (paymentDto.getDate() != null) {
                payment.setDate(paymentDto.getDate());
            }

            if (paymentDto.getAmount() != null) {
                payment.setAmount(paymentDto.getAmount());
            }

        }
        return payment;
    }
}
