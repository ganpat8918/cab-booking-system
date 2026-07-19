package com.ganpat.cabbooking.service;

import java.util.List;

import com.ganpat.cabbooking.dto.PaymentDto;

public interface PaymentService {

    PaymentDto makePayment(PaymentDto paymentDto);

    List<PaymentDto> getAllPayments();

    PaymentDto getPayment(Long id);

}