package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class EndpointController {
    private final CarService carService;

    @GetMapping(path = "/getAll")
    @ResponseBody
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(path = "/getCar/{id}")
    @ResponseBody
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
}
