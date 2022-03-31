package com.el.opu.carsup.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;

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
    private String ukrainianDate;
    private String currentBid;
    private Long lastModifiedTimestamp;
    private Long auctionDateMillis;
    private boolean canBuyNow;
    private String location;

    @OneToOne(optional = false, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "url_id", unique = true)
    @JsonManagedReference
    private CarPageInfo url;
}
