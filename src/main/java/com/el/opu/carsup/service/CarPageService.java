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

    public void savePages(List<CarPageInfo> carPageInfos) {
        carPageRepository.saveAllAndFlush(carPageInfos);
        log.info("Added {} pages successfully", carPageInfos.size());
    }

    public void savePage(CarPageInfo carPageInfo) {
        carPageRepository.saveAndFlush(carPageInfo);
    }

    public void updatePage(Long id, Car car) {
        CarPageInfo info = carPageRepository.getById(id);
        car.setUrl(info);
        carRepository.save(car);
        carPageRepository.save(info);
    }

    public boolean ifExists(CarPageInfo carPageInfo) {
        return carPageRepository.getCarPageInfoByUrl(carPageInfo.getUrl()).isPresent();
    }

    public CarPageInfo getByUrl(String url) {
        return carPageRepository.getCarPageInfoByUrl(url).orElse(null);
    }

    public List<CarPageInfo> getAll() {
        return carPageRepository.findAll();
    }
}
