package com.ganpat.cabbooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ganpat.cabbooking.dto.CabDto;
import com.ganpat.cabbooking.entity.Cab;
import com.ganpat.cabbooking.entity.Driver;
import com.ganpat.cabbooking.exception.DuplicateResourceException;
import com.ganpat.cabbooking.exception.ResourceNotFoundException;
import com.ganpat.cabbooking.repository.CabRepository;
import com.ganpat.cabbooking.repository.DriverRepository;
import com.ganpat.cabbooking.service.CabService;

@Service
public class CabServiceImpl implements CabService {

    private final CabRepository cabRepository;
    private final DriverRepository driverRepository;

    public CabServiceImpl(CabRepository cabRepository,
                          DriverRepository driverRepository) {
        this.cabRepository = cabRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public CabDto addCab(CabDto cabDto) {

        if (cabRepository.existsByCabNumber(cabDto.getCabNumber())) {
            throw new DuplicateResourceException("Cab already exists");
        }

        Driver driver = driverRepository.findById(cabDto.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        Cab cab = new Cab();

        cab.setCabNumber(cabDto.getCabNumber());
        cab.setModel(cabDto.getModel());
        cab.setColor(cabDto.getColor());
        cab.setCabType(cabDto.getCabType());
        cab.setAvailable(true);
        cab.setDriver(driver);

        Cab savedCab = cabRepository.save(cab);

        return mapToDto(savedCab);
    }

    @Override
    public List<CabDto> getAllCabs() {

        return cabRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CabDto getCabById(Long id) {

        Cab cab = cabRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cab not found"));

        return mapToDto(cab);
    }

    @Override
    public CabDto updateCab(Long id, CabDto cabDto) {

        Cab cab = cabRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cab not found"));

        Driver driver = driverRepository.findById(cabDto.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        cab.setCabNumber(cabDto.getCabNumber());
        cab.setModel(cabDto.getModel());
        cab.setColor(cabDto.getColor());
        cab.setCabType(cabDto.getCabType());
        cab.setAvailable(cabDto.getAvailable());
        cab.setDriver(driver);

        Cab updatedCab = cabRepository.save(cab);

        return mapToDto(updatedCab);
    }

    @Override
    public void deleteCab(Long id) {

        Cab cab = cabRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cab not found"));

        cabRepository.delete(cab);
    }

    private CabDto mapToDto(Cab cab) {

        return CabDto.builder()
                .id(cab.getId())
                .cabNumber(cab.getCabNumber())
                .model(cab.getModel())
                .color(cab.getColor())
                .cabType(cab.getCabType())
                .available(cab.getAvailable())
                .driverId(cab.getDriver().getId())
                .build();
    }
}