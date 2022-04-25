package com.el.opu.carsup.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private int status;
    private String cause;
    private boolean success;
}
