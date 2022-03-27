package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.CarPageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPageRepository extends JpaRepository<CarPageInfo, Long> {
}
