package com.ganpat.cabbooking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabDto {

    private Long id;

    private String cabNumber;

    private String model;

    private String color;

    private String cabType;

    private Boolean available;

    private Long driverId;
}