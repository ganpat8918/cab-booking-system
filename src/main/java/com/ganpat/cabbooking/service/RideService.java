package com.ganpat.cabbooking.service;

import java.util.List;

import com.ganpat.cabbooking.dto.RideDto;

public interface RideService {

    RideDto bookRide(RideDto rideDto);

    List<RideDto> getAllRides();

    RideDto getRide(Long id);

    RideDto completeRide(Long id);

    void cancelRide(Long id);

}