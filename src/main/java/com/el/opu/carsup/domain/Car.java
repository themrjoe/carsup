package com.el.opu.carsup.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Accessors(chain = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brand;
    private String model;
    private String series;
    private String engine;
    private String fuelType;
    private String vehicleType;
    private String primaryDamage;
    private String secondaryDamage;
    private String auctionName;
    private String conditionValue;
    private String odometrValue;
    private String carYear;
    private String saleDate;
    private String lotNumber;
    private String auctionDate;
    private String buyNowPrice;
}
