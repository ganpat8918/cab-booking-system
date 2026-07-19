package com.ganpat.cabbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganpat.cabbooking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}