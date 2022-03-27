package com.el.opu.carsup.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final RetrieverClient retrieverClient;
    String info;

    @Retryable(value = {ApiException.class}, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 4))
    public String getInfo() {
        try {
            info = retrieverClient.getIaaiPage();
            log.info("Successfully retrieved page");
        } catch (Exception e) {
            log.error("Error retrieving page", e);
        }
        return info;
    }
}
