package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.DeleteRequest;

public interface DeleteRequestRepository extends JpaRepository<DeleteRequest, Long> {
    void deleteByUserId(Long userId);
}
