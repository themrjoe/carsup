package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car getCarById(Long id);
}
