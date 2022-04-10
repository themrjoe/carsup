package com.el.opu.carsup.service;

import com.el.opu.carsup.domain.Car;
import com.el.opu.carsup.domain.Role;
import com.el.opu.carsup.domain.Status;
import com.el.opu.carsup.domain.User;
import com.el.opu.carsup.domain.dto.FavouriteDto;
import com.el.opu.carsup.repository.CarRepository;
import com.el.opu.carsup.repository.RoleRepository;
import com.el.opu.carsup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void addCarToFavourite(FavouriteDto dto, String username) {
        User user = userRepository.findByUserName(username);
        Car car = carRepository.findById(dto.getIdCar()).orElse(null);
        if (user == null || car == null) {
            log.warn("User id or car id = null. Cannot add to favourite");
            return;
        }
        List<Car> userCars = new ArrayList<>();
        userCars.add(car);
        user.setCars(userCars);
        userRepository.save(user);
    }

    public List<Car> getFavouriteCarsForUser(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getCars();
    }

    public void deleteFromFavourite(FavouriteDto dto, String username) {
        User user = userRepository.findByUserName(username);
        Car car = carRepository.findById(dto.getIdCar()).orElse(null);
        if (user == null || car == null || CollectionUtils.isEmpty(user.getCars())) {
            return;
        }
        List<Car> cars = user.getCars();
        cars.remove(car);
        user.setCars(cars);
        userRepository.save(user);
    }
}
