package com.el.opu.carsup.configuration;

import com.el.opu.carsup.api.RetrieverClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TrackingConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public RetrieverClient retrieverClient() {
        String baseUrl = applicationProperties.getApiProperties().getIaaiUrl();
        log.info("Creating client for {}", baseUrl);
        return Feign.builder()
                .decode404()
                .contract(new SpringMvcContract())
                .encoder(encoder())
                .decoder(new Decoder.Default())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .options(new Request.Options(15000, 60000))
                .target(RetrieverClient.class, baseUrl);
    }

    @NotNull
    private JacksonEncoder encoder() {
        return new JacksonEncoder(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true));
    }
}
