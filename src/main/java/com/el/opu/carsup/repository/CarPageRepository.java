package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.CarPageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarPageRepository extends JpaRepository<CarPageInfo, Long> {

    Optional<CarPageInfo> getCarPageInfoByUrl(String url);

    @Query(value = "SELECT * FROM car_page_info " +
            "ORDER BY last_queried_timestamp NULLS FIRST " +
            "LIMIT :limit", nativeQuery = true)
    List<CarPageInfo> getCarPageInfos(long limit);
}
