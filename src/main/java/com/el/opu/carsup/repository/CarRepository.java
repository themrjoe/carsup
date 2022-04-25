package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CarRepository extends PagingAndSortingRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Optional<Car> getCarByLotNumber(String lotNumber);

    List<Car> findAllByAuctionDateMillisLessThan(long timestamp);

    @Query(value = "SELECT DISTINCT vehicle_type FROM car", nativeQuery = true)
    List<String> getAllVehicleTypes();
}
