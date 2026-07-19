package com.ganpat.cabbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ganpat.cabbooking.dto.DriverDto;
import com.ganpat.cabbooking.entity.Driver;
import com.ganpat.cabbooking.exception.DuplicateResourceException;
import com.ganpat.cabbooking.exception.ResourceNotFoundException;
import com.ganpat.cabbooking.repository.DriverRepository;
import com.ganpat.cabbooking.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverDto registerDriver(DriverDto driverDto) {

        if (driverRepository.existsByEmail(driverDto.getEmail())) {
            throw new DuplicateResourceException("Driver email already exists");
        }

        if (driverRepository.existsByPhone(driverDto.getPhone())) {
            throw new DuplicateResourceException("Driver phone already exists");
        }

        Driver driver = new Driver();

        driver.setFullName(driverDto.getFullName());
        driver.setEmail(driverDto.getEmail());
        driver.setPhone(driverDto.getPhone());
        driver.setLicenseNumber(driverDto.getLicenseNumber());
        driver.setAvailable(true);
        driver.setRating(5.0);
        driver.setCreatedAt(LocalDateTime.now());

        Driver savedDriver = driverRepository.save(driver);

        return mapToDto(savedDriver);
    }

    @Override
    public List<DriverDto> getAllDrivers() {

        return driverRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDto getDriverById(Long id) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id : " + id));

        return mapToDto(driver);
    }

    @Override
    public DriverDto updateDriver(Long id, DriverDto driverDto) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id : " + id));

        driver.setFullName(driverDto.getFullName());
        driver.setEmail(driverDto.getEmail());
        driver.setPhone(driverDto.getPhone());
        driver.setLicenseNumber(driverDto.getLicenseNumber());

        Driver updatedDriver = driverRepository.save(driver);

        return mapToDto(updatedDriver);
    }

    @Override
    public void deleteDriver(Long id) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id : " + id));

        driverRepository.delete(driver);
    }

    private DriverDto mapToDto(Driver driver) {

        return DriverDto.builder()
                .id(driver.getId())
                .fullName(driver.getFullName())
                .email(driver.getEmail())
                .phone(driver.getPhone())
                .licenseNumber(driver.getLicenseNumber())
                .available(driver.getAvailable())
                .rating(driver.getRating())
                .build();
    }
}