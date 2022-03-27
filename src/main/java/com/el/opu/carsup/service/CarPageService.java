package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.repository.CarPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarPageService {

    private final CarPageRepository carPageRepository;

    public void savePages(List<CarPageInfo> carPageInfos) {
        carPageRepository.saveAllAndFlush(carPageInfos);
        log.info("Added {} pages successfully", carPageInfos.size());
    }

    public void savePage(CarPageInfo carPageInfo) {
        carPageRepository.save(carPageInfo);
    }

    public List<CarPageInfo> getAll() {
        return carPageRepository.findAll();
    }
}
