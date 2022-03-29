package com.el.opu.carsup.parser;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.service.CarPageService;
import com.el.opu.carsup.service.CarService;
import com.el.opu.carsup.service.resolver.FieldResolver;
import com.el.opu.carsup.utils.CarsupConstants;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Parser {

    private final FieldResolver resolver;
    private final CarPageService carPageService;
    private final CarService carService;

    public void parseMainPage(String page) {
        Document document = Jsoup.parse(page);
        Element links = document.select("h4.heading-7.rtl-disabled > a").first();
        CarPageInfo pageInfo = mapToCarPageInfo(links.attr("href"));
//        List<CarPageInfo> pages =
//                links.stream()
//                        .map(link -> link.attr("href"))
//                        .map(this::mapToCarPageInfo)
//                        .collect(Collectors.toList());
        if (carPageService.ifExists(pageInfo)) {
            return;
        }
        carPageService.savePage(pageInfo);
    }

    public void parseCarPage(String page) {
        Document document = Jsoup.parse(page);
        Element title = document.select("h1.heading-2.heading-2-semi.mb-0.rtl-disabled").first();
        Elements uls = document.select("div.tile-body > ul.data-list.data-list--details");
        Elements ulsSecondaryInfo = document.select("div.action-area__secondary-info > ul.data-list.data-list--details");
        Car car = mapToCar(uls, title, ulsSecondaryInfo);
        if (carService.ifExists(car)) {
            return;
        }
        carService.saveCar(car);
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
        return car;
    }

    private CarPageInfo mapToCarPageInfo(String link) {
        return new CarPageInfo().setUrl(link);
    }
}
