package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.repository.CarPageRepository;
import com.el.opu.carsup.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarPageService {

    private final CarPageRepository carPageRepository;
    private final CarRepository carRepository;

    public void savePage(CarPageInfo carPageInfo) {
        if (ifExists(carPageInfo)) {
            return;
        }
        carPageRepository.saveAndFlush(carPageInfo);
    }

    public void updatePage(CarPageInfo carPageInfo, Car car) {
        if (carRepository.getCarByLotNumber(car.getLotNumber()).isPresent()) {
            Car repoCar = carRepository.getCarByLotNumber(car.getLotNumber()).orElse(null);
            if (repoCar == null) {
                return;
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
        }
        CarPageInfo info = carPageRepository.getById(carPageInfo.getId());
        info.setLastQueriedTimestamp(carPageInfo.getLastQueriedTimestamp());
        car.setUrl(info);
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
