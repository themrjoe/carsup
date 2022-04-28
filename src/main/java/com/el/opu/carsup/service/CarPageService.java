package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.domain.ImageLink;
import com.el.opu.carsup.repository.CarPageRepository;
import com.el.opu.carsup.repository.CarRepository;
import com.el.opu.carsup.repository.ImageLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarPageService {

    private final CarPageRepository carPageRepository;
    private final CarRepository carRepository;
    private final ImageLinkRepository imageLinkRepository;

    public void savePage(CarPageInfo carPageInfo) {
        if (ifExists(carPageInfo)) {
            return;
        }
        carPageRepository.saveAndFlush(carPageInfo);
    }

    public void updatePage(CarPageInfo carPageInfo, Car car, List<ImageLink> imageLinks) {
        if (carRepository.getCarByLotNumber(car.getLotNumber()).isPresent()) {
            Car repoCar = carRepository.getCarByLotNumber(car.getLotNumber()).orElse(null);
            if (repoCar == null) {
                return;
            }
            List<ImageLink> finalLinks = new ArrayList<>();
            ListIterator<ImageLink> iterator = imageLinks.listIterator();
            while (iterator.hasNext()) {
                ImageLink imageLink = iterator.next();
                imageLink.setCar(repoCar);
                if (!imageLinkRepository.findByLink(imageLink.getLink()).isPresent()){
                    imageLinkRepository.saveAndFlush(imageLink);
                    finalLinks.add(imageLink);
                } else {
                    ImageLink repoLink = imageLinkRepository.findByLink(imageLink.getLink()).orElse(null);
                    if (repoCar.getId() != repoLink.getCar().getId()) {
                        repoLink.setCar(repoCar);
                        imageLinkRepository.saveAndFlush(repoLink);
                        finalLinks.add(repoLink);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(finalLinks)){
                repoCar.setImageLinks(finalLinks);
            }
            repoCar.setBrand(car.getBrand());
            repoCar.setCarYear(car.getCarYear());
            repoCar.setModel(car.getModel());
            repoCar.setSeries(car.getSeries());
            repoCar.setEngine(car.getEngine());
            repoCar.setFuelType(car.getFuelType());
            repoCar.setVehicleType(car.getVehicleType());
            repoCar.setPrimaryDamage(car.getPrimaryDamage());
            repoCar.setSecondaryDamage(car.getSecondaryDamage());
            repoCar.setConditionValue(car.getConditionValue());
            repoCar.setOdometrValue(car.getOdometrValue());
            repoCar.setAuctionDate(car.getAuctionDate());
            repoCar.setBuyNowPrice(car.getBuyNowPrice());
            repoCar.setUkrainianDate(car.getUkrainianDate());
            repoCar.setCurrentBid(car.getCurrentBid());
            repoCar.setLocation(car.getLocation());
            repoCar.setCanBuyNow(car.isCanBuyNow());
            repoCar.setAuctionDateMillis(car.getAuctionDateMillis());
            repoCar.setAuctionName(car.getAuctionName());
            CarPageInfo info = carPageRepository.getById(carPageInfo.getId());
            repoCar.setUrl(info);
            carRepository.save(repoCar);
            info.setCar(repoCar);
            carPageRepository.save(info);
            return;
        }
        CarPageInfo info = carPageRepository.getById(carPageInfo.getId());
        info.setLastQueriedTimestamp(carPageInfo.getLastQueriedTimestamp());
        car.setUrl(info);
        List<ImageLink> finalLinks = new ArrayList<>();
        ListIterator<ImageLink> iterator = imageLinks.listIterator();
        while (iterator.hasNext()) {
            ImageLink imageLink = iterator.next();
            imageLink.setCar(car);
            if (!imageLinkRepository.findByLink(imageLink.getLink()).isPresent()){
                imageLinkRepository.saveAndFlush(imageLink);
                finalLinks.add(imageLink);
            }
        }
        car.setImageLinks(finalLinks);
        carRepository.save(car);
        info.setCar(car);
        carPageRepository.save(info);
    }

    public boolean ifExists(CarPageInfo carPageInfo) {
        return carPageRepository.getCarPageInfoByUrl(carPageInfo.getUrl()).isPresent();
    }

    public CarPageInfo getByUrl(String url) {
        return carPageRepository.getCarPageInfoByUrl(url).orElse(null);
    }

    public List<CarPageInfo> getInfosByLimit(long limit) {
        return carPageRepository.getCarPageInfos(limit);
    }

    public long countAll() {
        return carPageRepository.count();
    }
}
