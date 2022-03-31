package com.el.opu.carsup.service;

import com.el.opu.carsup.api.ApiService;
import com.el.opu.carsup.api.model.CarLink;
import com.el.opu.carsup.configuration.ApplicationProperties;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiScenario {

    private final Parser parser;
    private final ApiService apiService;
    private final CarPageService carPageService;
    private final ApplicationProperties applicationProperties;

    public void getCars() {
        String page = apiService.getInfo();
        if (StringUtils.isEmpty(page)) {
            log.warn("Main page is not retrieved");
            return;
        }
        parser.parseMainPage(page);
        getAllCarsInfo(carPageService.getInfosByLimit(applicationProperties.getQueryLimit()));
    }

    private void getAllCarsInfo(List<CarPageInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            log.warn("No links for cars is available");
            return;
        }

        List<CarLink> listLinks = apiService.getCarInfo(infos.stream()
                .map(CarPageInfo::getUrl)
                .collect(Collectors.toList()));

        listLinks.forEach(parser::parseCarPage);
    }
}
