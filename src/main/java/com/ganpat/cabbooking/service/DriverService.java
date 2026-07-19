package com.ganpat.cabbooking.service;

import java.util.List;

import com.ganpat.cabbooking.dto.DriverDto;

public interface DriverService {

    DriverDto registerDriver(DriverDto driverDto);

    List<DriverDto> getAllDrivers();

    DriverDto getDriverById(Long id);

    DriverDto updateDriver(Long id, DriverDto driverDto);

    void deleteDriver(Long id);
}