package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.domain.ImageLink;
import com.el.opu.carsup.domain.User;
import com.el.opu.carsup.domain.dto.CarFavouriteDto;
import com.el.opu.carsup.domain.dto.ImgLinkDto;
import com.el.opu.carsup.repository.CarPageRepository;
import com.el.opu.carsup.repository.CarRepository;
import com.el.opu.carsup.repository.ImageLinkRepository;
import com.el.opu.carsup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final UserRepository userRepository;
    private final ImageLinkRepository imageLinkRepository;
    private final Clock clock;

    public CarFavouriteDto getCarById(Long id, String username) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return null;
        }
        boolean favouriteForUser;
        if (StringUtils.isBlank(username)) {
            favouriteForUser = false;
        } else {
            User user = userRepository.findByUserName(username);
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

        List<Long> idCars = carToDelete.stream().map(Car::getId).collect(Collectors.toList());
        for (Long id : idCars) {
            carRepository.deleteFavouriteCars(id);
        }
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

    public List<String> getAllFuelTypes() {
        return carRepository.getAllFuelTypes();
    }

    private ImgLinkDto mapToImgLinkDto(ImageLink imageLink) {
        ImgLinkDto imgLinkDto = new ImgLinkDto();
        imgLinkDto.setId(imageLink.getId());
        imgLinkDto.setLink(imageLink.getLink());
        return imgLinkDto;
    }
}
