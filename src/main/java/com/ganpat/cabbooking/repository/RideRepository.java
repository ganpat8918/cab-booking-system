package com.ganpat.cabbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganpat.cabbooking.entity.Ride;
import com.ganpat.cabbooking.enums.RideStatus;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByStatus(RideStatus status);

}