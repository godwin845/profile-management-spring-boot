package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.CareerVision;

import java.util.Optional;

public interface CareerVisionRepository extends JpaRepository<CareerVision, Long> {

    Optional<CareerVision> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
