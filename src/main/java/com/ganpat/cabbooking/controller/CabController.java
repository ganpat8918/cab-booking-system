package com.ganpat.cabbooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ganpat.cabbooking.dto.CabDto;
import com.ganpat.cabbooking.service.CabService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cabs")
public class CabController {

    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CabDto addCab(@Valid @RequestBody CabDto cabDto) {
        return cabService.addCab(cabDto);
    }

    @GetMapping
    public List<CabDto> getAllCabs() {
        return cabService.getAllCabs();
    }

    @GetMapping("/{id}")
    public CabDto getCabById(@PathVariable Long id) {
        return cabService.getCabById(id);
    }

    @PutMapping("/{id}")
    public CabDto updateCab(@PathVariable Long id,
                            @RequestBody CabDto cabDto) {
        return cabService.updateCab(id, cabDto);
    }

    @DeleteMapping("/{id}")
    public String deleteCab(@PathVariable Long id) {
        cabService.deleteCab(id);
        return "Cab deleted successfully";
    }
}