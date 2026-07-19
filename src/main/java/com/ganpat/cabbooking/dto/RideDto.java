package com.ganpat.cabbooking.dto;

import com.ganpat.cabbooking.enums.RideStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDto {

    private Long id;

    private String pickupLocation;

    private String destination;

    private Double distance;

    private Double fare;

    private RideStatus status;

    private Long userId;

    private Long driverId;

    private Long cabId;

}