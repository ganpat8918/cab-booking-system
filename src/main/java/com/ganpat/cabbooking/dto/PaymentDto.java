package com.ganpat.cabbooking.dto;

import com.ganpat.cabbooking.enums.PaymentStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;

    private Double amount;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

    private Long rideId;
}