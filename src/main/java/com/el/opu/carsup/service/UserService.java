package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.*;
import com.el.opu.carsup.domain.dto.CarFavouriteDto;
import com.el.opu.carsup.domain.dto.FavouriteDto;
import com.el.opu.carsup.domain.dto.ImgLinkDto;
import com.el.opu.carsup.repository.CarRepository;
import com.el.opu.carsup.repository.RoleRepository;
import com.el.opu.carsup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User register(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            return null;
        }
        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    public CarFavouriteDto addCarToFavourite(FavouriteDto dto, String username) {
        User user = userRepository.findByUserName(username);
        Car car = carRepository.findById(dto.getIdCar()).orElse(null);
        if (user == null || car == null) {
            log.warn("User id or car id = null. Cannot add to favourite");
            return null;
        }
        if (CollectionUtils.isEmpty(user.getCars())) {
            List<Car> cars = new ArrayList<>();
            cars.add(car);
            user.setCars(cars);
            userRepository.save(user);

            boolean favouriteForUser;
            if (StringUtils.isBlank(username)) {
                favouriteForUser = false;
            } else {
                if (user == null) {
                    favouriteForUser = false;
                } else {
                    favouriteForUser = user.getCars().contains(car);
                }
            }
            return CarFavouriteDto.builder()
                    .id(car.getId())
                    .brand(car.getBrand())
                    .model(car.getModel())
                    .series(car.getSeries())
                    .engine(car.getEngine())
                    .fuelType(car.getFuelType())
                    .vehicleType(car.getVehicleType())
                    .primaryDamage(car.getPrimaryDamage())
                    .secondaryDamage(car.getSecondaryDamage())
                    .auctionName(car.getAuctionName())
                    .conditionValue(car.getConditionValue())
                    .odometrValue(car.getOdometrValue())
                    .carYear(car.getCarYear())
                    .saleDate(car.getSaleDate())
                    .lotNumber(car.getLotNumber())
                    .auctionDate(car.getAuctionDate())
                    .buyNowPrice(car.getBuyNowPrice())
                    .ukrainianDate(car.getUkrainianDate())
                    .currentBid(car.getCurrentBid())
                    .canBuyNow(car.isCanBuyNow())
                    .location(car.getLocation())
                    .url(car.getUrl().getUrl())
                    .links(car.getImageLinks().stream().map(this::mapToImgLinkDto).collect(Collectors.toList()))
                    .isFavForUser(favouriteForUser)
                    .build();
        }
        List<Car> userCars = new ArrayList<>();
        if (userCars.contains(car)) {
            return null;
        }
        userCars.add(car);
        user.setCars(userCars);
        userRepository.save(user);

        boolean favouriteForUser;
        if (StringUtils.isBlank(username)) {
            favouriteForUser = false;
        } else {
            if (user == null) {
                favouriteForUser = false;
            } else {
                favouriteForUser = user.getCars().contains(car);
            }
        }
        return CarFavouriteDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .series(car.getSeries())
                .engine(car.getEngine())
                .fuelType(car.getFuelType())
                .vehicleType(car.getVehicleType())
                .primaryDamage(car.getPrimaryDamage())
                .secondaryDamage(car.getSecondaryDamage())
                .auctionName(car.getAuctionName())
                .conditionValue(car.getConditionValue())
                .odometrValue(car.getOdometrValue())
                .carYear(car.getCarYear())
                .saleDate(car.getSaleDate())
                .lotNumber(car.getLotNumber())
                .auctionDate(car.getAuctionDate())
                .buyNowPrice(car.getBuyNowPrice())
                .ukrainianDate(car.getUkrainianDate())
                .currentBid(car.getCurrentBid())
                .canBuyNow(car.isCanBuyNow())
                .location(car.getLocation())
                .url(car.getUrl().getUrl())
                .links(car.getImageLinks().stream().map(this::mapToImgLinkDto).collect(Collectors.toList()))
                .isFavForUser(favouriteForUser)
                .build();
    }

    public List<Car> getFavouriteCarsForUser(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getCars();
    }

    public CarFavouriteDto deleteFromFavourite(FavouriteDto dto, String username) {
        User user = userRepository.findByUserName(username);
        Car car = carRepository.findById(dto.getIdCar()).orElse(null);
        if (user == null || car == null || CollectionUtils.isEmpty(user.getCars())) {
            return null;
        }
        List<Car> cars = user.getCars();
        cars.remove(car);
        user.setCars(cars);
        userRepository.save(user);

        boolean favouriteForUser;
        if (StringUtils.isBlank(username)) {
            favouriteForUser = false;
        } else {
            if (user == null) {
                favouriteForUser = false;
            } else {
                favouriteForUser = user.getCars().contains(car);
            }
        }
        return CarFavouriteDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .series(car.getSeries())
                .engine(car.getEngine())
                .fuelType(car.getFuelType())
                .vehicleType(car.getVehicleType())
                .primaryDamage(car.getPrimaryDamage())
                .secondaryDamage(car.getSecondaryDamage())
                .auctionName(car.getAuctionName())
                .conditionValue(car.getConditionValue())
                .odometrValue(car.getOdometrValue())
                .carYear(car.getCarYear())
                .saleDate(car.getSaleDate())
                .lotNumber(car.getLotNumber())
                .auctionDate(car.getAuctionDate())
                .buyNowPrice(car.getBuyNowPrice())
                .ukrainianDate(car.getUkrainianDate())
                .currentBid(car.getCurrentBid())
                .canBuyNow(car.isCanBuyNow())
                .location(car.getLocation())
                .url(car.getUrl().getUrl())
                .links(car.getImageLinks().stream().map(this::mapToImgLinkDto).collect(Collectors.toList()))
                .isFavForUser(favouriteForUser)
                .build();
    }

    private ImgLinkDto mapToImgLinkDto(ImageLink imageLink) {
        ImgLinkDto imgLinkDto = new ImgLinkDto();
        imgLinkDto.setId(imageLink.getId());
        imgLinkDto.setLink(imageLink.getLink());
        return imgLinkDto;
    }
}
