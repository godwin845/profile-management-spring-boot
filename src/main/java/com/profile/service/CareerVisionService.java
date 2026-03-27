package com.profile.service;

import org.springframework.stereotype.Service;

import com.profile.entity.CareerVision;
import com.profile.repository.CareerVisionRepository;

@Service
public class CareerVisionService {

    private final CareerVisionRepository repo;

    public CareerVisionService(CareerVisionRepository repo) {
        this.repo = repo;
    }

    public CareerVision getCareerVision(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    public CareerVision createCareerVision(CareerVision request) {
        repo.findByUserId(request.getUserId()).ifPresent(v -> {
            throw new RuntimeException("Career vision already exists");
        });

        return repo.save(request);
    }

    public CareerVision updateCareerVision(CareerVision request) {
        CareerVision vision = repo.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Career vision not found"));

        vision.setCategory(request.getCategory());
        vision.setField(request.getField());
        vision.setLongTerm(request.getLongTerm());
        vision.setShortTerm(request.getShortTerm());
        vision.setInspiration(request.getInspiration());

        return repo.save(vision);
    }

    public String deleteCareerVision(Long userId) {
        CareerVision vision = repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Career vision not found"));

        repo.delete(vision);
        return "Career vision deleted successfully";
    }
}
