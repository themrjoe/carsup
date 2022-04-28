package com.el.opu.carsup.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CarFavouriteDto {

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
    private boolean canBuyNow;
    private String location;
    private String url;
    private List<ImgLinkDto> links;
    private boolean isFavForUser;
}
