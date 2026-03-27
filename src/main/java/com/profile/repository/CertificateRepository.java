package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Certificate;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
