package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CarRepository extends PagingAndSortingRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Optional<Car> getCarByLotNumber(String lotNumber);

    List<Car> findAllByAuctionDateMillisLessThan(long timestamp);

    @Query(value = "SELECT DISTINCT vehicle_type FROM car", nativeQuery = true)
    List<String> getAllVehicleTypes();

    @Query(value = "SELECT DISTINCT fuel_type FROM car", nativeQuery = true)
    List<String> getAllFuelTypes();

    @Modifying
    @Query(value = "DELETE FROM user_cars WHERE car_id = :car_id", nativeQuery = true)
    void deleteFavouriteCars(@Param("car_id") Long car_id);
}
