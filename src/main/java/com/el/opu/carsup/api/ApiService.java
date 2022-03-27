package com.el.opu.carsup.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final RetrieverClient retrieverClient;

    @Retryable(value = {ApiException.class}, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 4))
    public String getInfo() {
        try {
            return retrieverClient.getIaaiPage();
        } catch (Exception e) {
            log.error("Error retrieving page", e);
            return null;
        }
    }

    @Retryable(value = {ApiException.class}, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 4))
    public List<String> getCarInfo(List<String> links) {
        return links.stream()
                .map(link -> {
                    try {
                        return retrieverClient.getCar(link);
                    } catch (Exception e) {
                        log.error("Error retrieving page of car by link: {}", link);
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
