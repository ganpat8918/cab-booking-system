package com.ganpat.cabbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ganpat.cabbooking.dto.PaymentDto;
import com.ganpat.cabbooking.entity.Payment;
import com.ganpat.cabbooking.entity.Ride;
import com.ganpat.cabbooking.enums.PaymentStatus;
import com.ganpat.cabbooking.exception.ResourceNotFoundException;
import com.ganpat.cabbooking.repository.PaymentRepository;
import com.ganpat.cabbooking.repository.RideRepository;
import com.ganpat.cabbooking.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RideRepository rideRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              RideRepository rideRepository) {
        this.paymentRepository = paymentRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public PaymentDto makePayment(PaymentDto paymentDto) {

        Ride ride = rideRepository.findById(paymentDto.getRideId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ride not found with id : "
                                + paymentDto.getRideId()));

        Payment payment = new Payment();

        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setRide(ride);

        Payment savedPayment = paymentRepository.save(payment);

        return mapToDto(savedPayment);
    }

    @Override
    public List<PaymentDto> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto getPayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + id));

        return mapToDto(payment);
    }

    private PaymentDto mapToDto(Payment payment) {

        return PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .rideId(payment.getRide().getId())
                .build();
    }
}