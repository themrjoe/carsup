package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.repository.CarRepository;
import com.sipios.springsearch.anotation.SearchSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FilterController {

    private final CarRepository carRepository;

    @GetMapping("cars/filter")
    @ResponseBody
    List<Car> getAllWhereBrandIsChevrolet(@SearchSpec Specification<Car> specification) {
        return carRepository.findAll(Specification.where(specification));
    }
}
