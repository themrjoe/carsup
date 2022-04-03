package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.dto.FavouriteDto;
import com.el.opu.carsup.service.CarService;
import com.el.opu.carsup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class EndpointController {

    private final UserService userService;
    private final CarService carService;

    @GetMapping(path = "/cars/all")
    @ResponseBody
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(path = "/cars/{id}")
    @ResponseBody
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping(path = "/user/add_to_fav", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addToFavourite(@RequestBody FavouriteDto dto) {
        userService.addCarToFavourite(dto);
    }
}
