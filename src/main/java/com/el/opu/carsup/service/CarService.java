package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.domain.ImageLink;
import com.el.opu.carsup.repository.CarPageRepository;
import com.el.opu.carsup.repository.CarRepository;
import com.el.opu.carsup.repository.ImageLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarPageRepository carPageRepository;
    private final ImageLinkRepository imageLinkRepository;
    private final Clock clock;

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getAllCars(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 100);
        Page<Car> carPage = carRepository.findAll(pageable);
        return carPage.toList();
    }

    public long getCountCars() {
        return carRepository.count();
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public long cleanDb() {
        List<Car> carToDelete = carRepository.findAllByAuctionDateMillisLessThan(clock.instant().toEpochMilli());
        List<CarPageInfo> infosToDelete = carToDelete.stream()
                .map(Car::getUrl)
                .collect(Collectors.toList());
        List<ImageLink> imagesToDelete = carToDelete.stream()
                .map(Car::getImageLinks)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        imageLinkRepository.deleteAll(imagesToDelete);
        carRepository.deleteAll(carToDelete);
        carPageRepository.deleteAll(infosToDelete);
        return carToDelete.size();
    }

    public boolean ifExists(Car car) {
        return carRepository.getCarByLotNumber(car.getLotNumber()).isPresent();
    }

    public List<String> getAllVehicleTypes() {
        return carRepository.getAllVehicleTypes();
    }
}
