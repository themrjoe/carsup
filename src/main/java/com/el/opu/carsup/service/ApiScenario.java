package com.el.opu.carsup.service;

import com.el.opu.carsup.api.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiScenario {
    private final ApiService apiService;

    public void getCars() {
        String page = apiService.getInfo();
    }
}
