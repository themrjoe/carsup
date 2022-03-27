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
    private String vehicleType;
    private String damage;
    private String auctionName;
    private String conditionValue;
    private String odometrValue;
    private String carYear;
    private String saleDate;
    private Long lotNumber;
    private String status;
}
