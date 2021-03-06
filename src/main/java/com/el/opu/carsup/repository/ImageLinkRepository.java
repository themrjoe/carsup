package com.el.opu.carsup.repository;

import com.el.opu.carsup.domain.ImageLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageLinkRepository extends JpaRepository<ImageLink, Long> {

    Optional<ImageLink> findByLink(String link);
}
