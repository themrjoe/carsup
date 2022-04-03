package com.el.opu.carsup.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "el-opu-carsup")
public class ApplicationProperties {

    @NotNull
    @Valid
    private ApiProperties apiProperties;

    @NotNull
    private Long queryLimit;

    @NotBlank
    private String jwtTokenSecret;

    @NotNull
    private Long jwtTokenExpired;
}
