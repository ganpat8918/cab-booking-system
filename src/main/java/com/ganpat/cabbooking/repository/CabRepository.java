package com.ganpat.cabbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganpat.cabbooking.entity.Cab;

public interface CabRepository extends JpaRepository<Cab, Long> {

    boolean existsByCabNumber(String cabNumber);

}