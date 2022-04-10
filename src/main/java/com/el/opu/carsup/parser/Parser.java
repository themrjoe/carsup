package com.el.opu.carsup.parser;

import com.el.opu.carsup.api.model.CarLink;
import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.service.CarPageService;
import com.el.opu.carsup.service.CarService;
import com.el.opu.carsup.service.resolver.FieldResolver;
import com.el.opu.carsup.utils.CarsupConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Slf4j
@Service
@RequiredArgsConstructor
public class Parser {

    private final FieldResolver resolver;
    private final CarPageService carPageService;
    private final CarService carService;
    private final Clock clock;

    private static final long TABLE_MAX_LIMIT = 4500;

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
        carPageService.updatePage(carPageInfo, car);
    }

    private Car mapToCar(Elements uls, Element title, Elements ulsSecondaryInfo) {
        Car car = new Car();
        car.setBrand(resolver.resolveBrandName(title.text()));
        car.setCarYear(resolver.resolveCarYear(title.text()));
        car.setModel(resolver.resolveField(uls, CarsupConstants.MODEL));
        car.setSeries(resolver.resolveField(uls, CarsupConstants.SERIES));
        car.setEngine(resolver.resolveField(uls, CarsupConstants.ENGINE));
        car.setFuelType(resolver.resolveField(uls, CarsupConstants.FUEL_TYPE));
        car.setVehicleType(resolver.resolveField(uls, CarsupConstants.VEHICLE_TYPE));
        car.setPrimaryDamage(resolver.resolveField(uls, CarsupConstants.PRIMARY_DAMAGE));
        car.setSecondaryDamage(resolver.resolveField(uls, CarsupConstants.SECONDARY_DAMAGE));
        car.setConditionValue(resolver.resolveField(uls, CarsupConstants.CONDITION));
        car.setOdometrValue(resolver.resolveOdometerValue(resolver.resolveField(uls, CarsupConstants.ODOMETER)));
        car.setLotNumber(resolver.getLotNumber(resolver.resolveField(uls, CarsupConstants.STOCK)));
        car.setAuctionDate(resolver.resolveAuctionDateTime(uls));
        car.setBuyNowPrice(resolver.resolvePrice(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.BUY_NOW_PRICE)));
        car.setUkrainianDate(resolver.dateToUkrainianDate(car.getAuctionDate()));
        car.setCurrentBid(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.CURRENT_BID));
        car.setLocation(resolver.resolveField(uls, CarsupConstants.SELLING_BRANCH));
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
