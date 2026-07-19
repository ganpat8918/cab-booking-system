package com.ganpat.cabbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ganpat.cabbooking.dto.RideDto;
import com.ganpat.cabbooking.entity.Cab;
import com.ganpat.cabbooking.entity.Driver;
import com.ganpat.cabbooking.entity.Ride;
import com.ganpat.cabbooking.entity.User;
import com.ganpat.cabbooking.enums.RideStatus;
import com.ganpat.cabbooking.exception.ResourceNotFoundException;
import com.ganpat.cabbooking.repository.CabRepository;
import com.ganpat.cabbooking.repository.DriverRepository;
import com.ganpat.cabbooking.repository.RideRepository;
import com.ganpat.cabbooking.repository.UserRepository;
import com.ganpat.cabbooking.service.RideService;

@Service
@Transactional
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final CabRepository cabRepository;

    public RideServiceImpl(
            RideRepository rideRepository,
            UserRepository userRepository,
            DriverRepository driverRepository,
            CabRepository cabRepository) {

        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.cabRepository = cabRepository;
    }

    @Override
    public RideDto bookRide(RideDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        Cab cab = cabRepository.findById(dto.getCabId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cab not found"));

        Ride ride = new Ride();

        ride.setPickupLocation(dto.getPickupLocation());
        ride.setDestination(dto.getDestination());
        ride.setDistance(dto.getDistance());

        // Fare Calculation
        ride.setFare(dto.getDistance() * 15);

        ride.setBookingTime(LocalDateTime.now());
        ride.setStatus(RideStatus.BOOKED);

        ride.setUser(user);
        ride.setDriver(driver);
        ride.setCab(cab);

        driver.setAvailable(false);
        cab.setAvailable(false);

        Ride savedRide = rideRepository.save(ride);

        return mapToDto(savedRide);
    }

    @Override
    public List<RideDto> getAllRides() {

        return rideRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RideDto getRide(Long id) {

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ride not found"));

        return mapToDto(ride);
    }

    @Override
    public RideDto completeRide(Long id) {

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ride not found"));

        ride.setStatus(RideStatus.COMPLETED);

        ride.getDriver().setAvailable(true);
        ride.getCab().setAvailable(true);

        return mapToDto(rideRepository.save(ride));
    }

    @Override
    public void cancelRide(Long id) {

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ride not found"));

        ride.setStatus(RideStatus.CANCELLED);

        ride.getDriver().setAvailable(true);
        ride.getCab().setAvailable(true);

        rideRepository.save(ride);
    }

    private RideDto mapToDto(Ride ride) {

        return RideDto.builder()
                .id(ride.getId())
                .pickupLocation(ride.getPickupLocation())
                .destination(ride.getDestination())
                .distance(ride.getDistance())
                .fare(ride.getFare())
                .status(ride.getStatus())
                .userId(ride.getUser().getId())
                .driverId(ride.getDriver().getId())
                .cabId(ride.getCab().getId())
                .build();
    }
}