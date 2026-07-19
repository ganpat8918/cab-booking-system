package com.ganpat.cabbooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ganpat.cabbooking.dto.DriverDto;
import com.ganpat.cabbooking.service.DriverService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drivers")
@Validated
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDto registerDriver(@Valid @RequestBody DriverDto driverDto) {
        return driverService.registerDriver(driverDto);
    }

    @GetMapping
    public List<DriverDto> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverDto getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PutMapping("/{id}")
    public DriverDto updateDriver(@PathVariable Long id,
                                  @RequestBody DriverDto driverDto) {
        return driverService.updateDriver(id, driverDto);
    }

    @DeleteMapping("/{id}")
    public String deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "Driver deleted successfully";
    }
}