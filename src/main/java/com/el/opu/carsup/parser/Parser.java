package com.el.opu.carsup.parser;

import com.el.opu.carsup.api.model.CarLink;
import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.domain.ImageLink;
import com.el.opu.carsup.domain.dto.CarDto;
import com.el.opu.carsup.domain.dto.Img;
import com.el.opu.carsup.service.CarPageService;
import com.el.opu.carsup.service.resolver.FieldResolver;
import com.el.opu.carsup.utils.CarsupConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class Parser {

    private final FieldResolver resolver;
    private final CarPageService carPageService;
    private final Clock clock;
    private final ObjectMapper objectMapper;

    private static final long TABLE_MAX_LIMIT = 1000;

    public void parseMainPage(String page) {
        Document document = Jsoup.parse(page);
        Elements links = document.select("h4.heading-7.rtl-disabled > a");
        links.stream()
                .map(link -> link.attr("href"))
                .filter(StringUtils::isNotBlank)
                .map(this::mapToCarPageInfo)
                .forEach(carPageInfo -> {
                    if (carPageService.countAll() >= TABLE_MAX_LIMIT) {
                        return;
                    }
                    carPageService.savePage(carPageInfo);
                });
    }

    public void parseCarPage(CarLink carLink) {
        Document document = Jsoup.parse(carLink.getHtmlCarDataPage());
        Element carInfo = document.getElementById("ProductDetailsVM");
        if (carInfo == null) {
            return;
        }
        String infoJson = carInfo.data();
        infoJson = infoJson.substring(6).replace("$", "");
        CarDto carDto = null;
        try {
            carDto = objectMapper.readValue(infoJson, CarDto.class);
        } catch (JsonProcessingException e) {
            log.warn("Cannot process car info");
        }
        Element title = document.select("h1.heading-2.heading-2-semi.mb-0.rtl-disabled").first();
        Elements uls = document.select("div.tile-body > ul.data-list.data-list--details");
        Elements ulsSecondaryInfo = document.select("div.action-area__secondary-info > ul.data-list.data-list--details");
        if (title == null) {
            return;
        }
        Car car = mapToCar(uls, title, ulsSecondaryInfo);
        CarPageInfo carPageInfo = carPageService.getByUrl(carLink.getLink());
        if (carPageInfo == null) {
            return;
        }
        carPageInfo.setLastQueriedTimestamp(clock.instant().toEpochMilli());
        if (StringUtils.isBlank(car.getLotNumber())) {
            return;
        }
        List<ImageLink> imageLinks;
        if (carDto == null) {
            imageLinks = null;
        }
        imageLinks = mapToImages(carDto.getAuctionInformation().getImageInformation().getImages().getValues());
        carPageService.updatePage(carPageInfo, car, imageLinks);
    }

    private List<ImageLink> mapToImages(List<Img> values) {
        return values.stream().map(value -> {
            ImageLink imageLink = new ImageLink();
            imageLink.setLink(value.getUrl());
            return imageLink;
        }).limit(5).collect(Collectors.toList());
    }

    private Car mapToCar(Elements uls, Element title, Elements ulsSecondaryInfo) {
        Car car = new Car();
        car.setBrand(resolver.resolveBrandName(title.text()));
        car.setCarYear(resolver.resolveCarYear(title.text()));
        car.setModel(resolver.resolveModel(resolver.resolveField(uls, CarsupConstants.MODEL)));
        car.setSeries(resolver.resolveSeries(resolver.resolveField(uls, CarsupConstants.SERIES)));
        car.setEngine(resolver.resolveEngine(resolver.resolveField(uls, CarsupConstants.ENGINE)));
        car.setFuelType(resolver.resolveFuelType(resolver.resolveField(uls, CarsupConstants.FUEL_TYPE)));
        car.setVehicleType(resolver.resolveVehicle(resolver.resolveField(uls, CarsupConstants.VEHICLE_TYPE)));
        car.setPrimaryDamage(resolver.resolvePrimaryDamage(resolver.resolveField(uls, CarsupConstants.PRIMARY_DAMAGE)));
        car.setSecondaryDamage(resolver.resolveSecondaryDamage(resolver.resolveField(uls, CarsupConstants.SECONDARY_DAMAGE)));
        car.setConditionValue(resolver.resolveConditionalValue(resolver.resolveField(uls, CarsupConstants.CONDITION)));
        car.setOdometrValue(resolver.resolveOdometerValue(resolver.resolveField(uls, CarsupConstants.ODOMETER)));
        car.setLotNumber(resolver.getLotNumber(resolver.resolveField(uls, CarsupConstants.STOCK)));
        car.setAuctionDate(resolver.resolveAuctionDateTime(uls));
        car.setBuyNowPrice(resolver.resolveBuyNowPrice(resolver.resolvePrice(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.BUY_NOW_PRICE))));
        car.setUkrainianDate(resolver.dateToUkrainianDate(car.getAuctionDate()));
        car.setCurrentBid(resolver.resolveCurrentBid(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.CURRENT_BID)));
        car.setLocation(resolver.resolveLocation(resolver.resolveField(uls, CarsupConstants.SELLING_BRANCH)));
        car.setCanBuyNow(StringUtils.isNotBlank(car.getBuyNowPrice()));
        car.setLastModifiedTimestamp(clock.instant().toEpochMilli());
        car.setAuctionDateMillis(resolver.dateToMillisInUtc(car.getUkrainianDate()));
        car.setAuctionName("IAAI");
        return car;
    }

    private CarPageInfo mapToCarPageInfo(String link) {
        return new CarPageInfo().setUrl(link);
    }
}
