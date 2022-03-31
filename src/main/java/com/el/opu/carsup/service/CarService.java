package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final Clock clock;

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public long cleanDb() {
        return carRepository.deleteAllByAuctionDateMillisLessThan(clock.instant().toEpochMilli());
    }

    public boolean ifExists(Car car) {
        return carRepository.getCarByLotNumber(car.getLotNumber()).isPresent();
    }
}
