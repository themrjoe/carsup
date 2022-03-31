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

    private static final int TABLE_MAX_LIMIT = 4500;
    private int limit = 0;

    public void parseMainPage(String page) {
        Document document = Jsoup.parse(page);
        Element links = document.select("h4.heading-7.rtl-disabled > a").first();
        CarPageInfo pageInfo = mapToCarPageInfo(links.attr("href"));
//        List<CarPageInfo> pages =
//                links.stream()
//                        .map(link -> link.attr("href"))
//                        .map(this::mapToCarPageInfo)
//                        .collect(Collectors.toList());
        if (carPageService.ifExists(pageInfo) || limit == TABLE_MAX_LIMIT) {
            log.warn("Page exists now or limit is reached. Page: {}. Limit: {}", page, limit);
            return;
        }
        carPageService.savePage(pageInfo);
        limit += 1;
    }

    public void parseCarPage(CarLink carLink) {
        Document document = Jsoup.parse(carLink.getHtmlCarDataPage());
        Element title = document.select("h1.heading-2.heading-2-semi.mb-0.rtl-disabled").first();
        Elements uls = document.select("div.tile-body > ul.data-list.data-list--details");
        Elements ulsSecondaryInfo = document.select("div.action-area__secondary-info > ul.data-list.data-list--details");
        Car car = mapToCar(uls, title, ulsSecondaryInfo);
        CarPageInfo carPageInfo = carPageService.getByUrl(carLink.getLink());
        if (carPageInfo == null) {
            return;
        }
        carPageInfo.setLastQueriedTimestamp(clock.instant().toEpochMilli());
        if (carService.ifExists(car)) {
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
        car.setLotNumber(resolver.resolveField(uls, CarsupConstants.STOCK));
        car.setAuctionDate(resolver.resolveAuctionDateTime(uls));
        car.setBuyNowPrice(resolver.resolvePrice(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.BUY_NOW_PRICE)));
        car.setUkrainianDate(resolver.dateToUkrainianDate(car.getAuctionDate()));
        car.setCurrentBid(resolver.resolveField(ulsSecondaryInfo, CarsupConstants.CURRENT_BID));
        car.setLocation(resolver.resolveField(uls, CarsupConstants.SELLING_BRANCH));
        car.setCanBuyNow(StringUtils.isNotBlank(car.getBuyNowPrice()));
        car.setLastModifiedTimestamp(clock.instant().toEpochMilli());
        car.setAuctionDateMillis(resolver.dateToMillisInUtc(car.getUkrainianDate()));
        return car;
    }

    private CarPageInfo mapToCarPageInfo(String link) {
        return new CarPageInfo().setUrl(link);
    }
}
