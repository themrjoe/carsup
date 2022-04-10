package com.el.opu.carsup.service;

import com.el.opu.carsup.api.ApiService;
import com.el.opu.carsup.configuration.ApplicationProperties;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.parser.Parser;
import com.el.opu.carsup.utils.CarsupConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiScenario {

    private final Parser parser;
    private final ApiService apiService;
    private final CarPageService carPageService;
    private final ApplicationProperties applicationProperties;

    public void getCars() {
        CarsupConstants.PAGE_LINKS.forEach(link -> {
            String page = apiService.getInfo(link);
            if (StringUtils.isEmpty(page)) {
                log.warn("Main page is not retrieved Page: {}", link);
                return;
            }
            parser.parseMainPage(page);
        });
    }

    public void getAllCarsInfo() {
        List<CarPageInfo> infos = carPageService.getInfosByLimit(applicationProperties.getQueryLimit());
        if (CollectionUtils.isEmpty(infos)) {
            log.warn("No links for cars is available");
            return;
        }
        infos.stream()
                .map(CarPageInfo::getUrl)
                .filter(Objects::nonNull)
                .map(apiService::getCarInfo)
                .filter(Objects::nonNull)
                .filter(carLink -> StringUtils.isNotBlank(carLink.getHtmlCarDataPage()))
                .forEach(parser::parseCarPage);
    }
}
