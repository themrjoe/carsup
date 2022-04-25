package com.el.opu.carsup.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarDto {

    private Information auctionInformation;
}
