package com.ganpat.cabbooking.service;

import java.util.List;

import com.ganpat.cabbooking.dto.CabDto;

public interface CabService {

    CabDto addCab(CabDto cabDto);

    List<CabDto> getAllCabs();

    CabDto getCabById(Long id);

    CabDto updateCab(Long id, CabDto cabDto);

    void deleteCab(Long id);

}