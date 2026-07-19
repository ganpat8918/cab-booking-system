package com.ganpat.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String licenseNumber;
    private Boolean available;
    private Double rating;
}