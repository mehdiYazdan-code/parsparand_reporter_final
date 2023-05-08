package com.armaninvestment.parsparandreporter.mappers;

import com.armaninvestment.parsparandreporter.dtos.CustomerDto;
import com.armaninvestment.parsparandreporter.dtos.PaymentDto;
import com.armaninvestment.parsparandreporter.entities.Customer;
import com.armaninvestment.parsparandreporter.entities.Payment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CustomerMapperImpl implements CustomerMapper {
    public CustomerMapperImpl() {
    }

    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        } else {
            Customer customer = new Customer();
            customer.setId(customerDto.getId());
            customer.setName(customerDto.getName());
            customer.setPhone(customerDto.getPhone());
            customer.setPayments(this.paymentDtoSetToPaymentSet(customerDto.getPayments()));
            this.linkPayments(customer);
            return customer;
        }
    }

    public CustomerDto customerToCustomerDto(Customer customer) {
        if (customer == null) {
            return null;
        } else {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setPhone(customer.getPhone());
            customerDto.setPayments(this.paymentSetToPaymentDtoSet(customer.getPayments()));
            return customerDto;
        }
    }

    public Customer updateCustomerFromCustomerDto(CustomerDto customerDto, Customer customer) {
        if (customerDto == null) {
            return customer;
        } else {
            if (customerDto.getId() != null) {
                customer.setId(customerDto.getId());
            }

            if (customerDto.getName() != null) {
                customer.setName(customerDto.getName());
            }

            if (customerDto.getPhone() != null) {
                customer.setPhone(customerDto.getPhone());
            }

            List list;
            if (customer.getPayments() != null) {
                list = this.paymentDtoSetToPaymentSet(customerDto.getPayments());
                if (list != null) {
                    customer.getPayments().clear();
                    customer.getPayments().addAll(list);
                }
            } else {
                list = this.paymentDtoSetToPaymentSet(customerDto.getPayments());
                if (list != null) {
                    customer.setPayments(list);
                }
            }

            this.linkPayments(customer);
            return customer;
        }
    }

    protected Payment paymentDtoToPayment(PaymentDto paymentDto) {
        return getPayment(paymentDto);
    }

    static Payment getPayment(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        } else {
            Payment payment = new Payment();
            payment.setId(paymentDto.getId());
            payment.setDescription(paymentDto.getDescription());
            payment.setDate(paymentDto.getDate());
            payment.setAmount(paymentDto.getAmount());
            return payment;
        }
    }

    protected List<Payment> paymentDtoSetToPaymentSet(List<PaymentDto> set) {
        if (set == null) {
            return null;
        } else {
            List<Payment> list1 = new ArrayList<>(Math.max((int)((float)set.size() / 0.75F) + 1, 16));

            for (PaymentDto paymentDto : set) {
                list1.add(this.paymentDtoToPayment(paymentDto));
            }

            return list1;
        }
    }

    protected PaymentDto paymentToPaymentDto(Payment payment) {
        return getPaymentDto(payment);
    }

    static PaymentDto getPaymentDto(Payment payment) {
        if (payment == null) {
            return null;
        } else {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setDescription(payment.getDescription());
            paymentDto.setDate(payment.getDate());
            paymentDto.setAmount(payment.getAmount());
            return paymentDto;
        }
    }

    protected List<PaymentDto> paymentSetToPaymentDtoSet(List<Payment> set) {
        if (set == null) {
            return null;
        } else {
            ArrayList<PaymentDto> paymentDtos = new ArrayList<>(Math.max((int) ((float) set.size() / 0.75F) + 1, 16));

            for (Payment payment : set) {
                paymentDtos.add(this.paymentToPaymentDto(payment));
            }

            return paymentDtos;
        }
    }
}
