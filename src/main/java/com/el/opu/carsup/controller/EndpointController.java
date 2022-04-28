package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.dto.CarFavouriteDto;
import com.el.opu.carsup.domain.dto.FavouriteDto;
import com.el.opu.carsup.jwt.JwtTokenProvider;
import com.el.opu.carsup.service.CarService;
import com.el.opu.carsup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class EndpointController {

    private final UserService userService;
    private final CarService carService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(path = "/cars/all")
    @ResponseBody
    public List<Car> getAllCars(@RequestParam int pageNo) {
        return carService.getAllCars(pageNo);
    }

    @GetMapping(path = "/cars/count")
    @ResponseBody
    public long getCountCars() {
        return carService.getCountCars();
    }

    @GetMapping(path = "/cars/{id}")
    @ResponseBody
    public CarFavouriteDto getCarById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (StringUtils.isBlank(token)){
            return carService.getCarById(id, "");
        }
        return carService.getCarById(id, resolveUsernameByToken(token));
    }

    @GetMapping(path = "/cars/vehicleTypes")
    @ResponseBody
    public List<String> getAllVehicleTypes() {
        return carService.getAllVehicleTypes();
    }

    @GetMapping(path = "/cars/fuelTypes")
    @ResponseBody
    public List<String> getFuelTypes() {
        return carService.getAllFuelTypes();
    }

    @PostMapping(path = "/user/add_to_fav", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addToFavourite(@RequestBody FavouriteDto dto, @RequestHeader("Authorization") String token) {
        userService.addCarToFavourite(dto, resolveUsernameByToken(token));
    }

    @GetMapping(path = "/user/get_fav")
    @ResponseBody
    public List<Car> getFavouriteCars(@RequestHeader("Authorization") String token) {
        return userService.getFavouriteCarsForUser(resolveUsernameByToken(token));
    }

    @DeleteMapping(path = "/user/delete_from_fav")
    @ResponseBody
    public void deleteCarFromFavourite(@RequestHeader("Authorization") String token, FavouriteDto dto) {
        userService.deleteFromFavourite(dto, resolveUsernameByToken(token));
    }

    private String resolveUsernameByToken(String token) {
        return jwtTokenProvider.getUserName(jwtTokenProvider.resolveTokenFromHeader(token));
    }
}
