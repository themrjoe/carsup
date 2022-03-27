package com.el.opu.carsup.configuration;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApiProperties {

    @NotBlank
    private String iaaiUrl;
    @NotBlank
    private String secondUrl;
}
