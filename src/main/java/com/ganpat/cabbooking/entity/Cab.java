package com.ganpat.cabbooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cabs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cabNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String cabType;

    @Column(nullable = false)
    private Boolean available;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}