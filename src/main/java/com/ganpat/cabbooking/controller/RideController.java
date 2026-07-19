package com.ganpat.cabbooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ganpat.cabbooking.dto.RideDto;
import com.ganpat.cabbooking.service.RideService;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public RideDto bookRide(@RequestBody RideDto rideDto) {
        return rideService.bookRide(rideDto);
    }

    @GetMapping
    public List<RideDto> getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("/{id}")
    public RideDto getRide(@PathVariable Long id) {
        return rideService.getRide(id);
    }

    @PutMapping("/{id}/complete")
    public RideDto completeRide(@PathVariable Long id) {
        return rideService.completeRide(id);
    }

    @DeleteMapping("/{id}")
    public String cancelRide(@PathVariable Long id) {
        rideService.cancelRide(id);
        return "Ride cancelled successfully";
    }
}