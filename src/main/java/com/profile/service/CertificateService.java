package com.profile.service;

import org.springframework.stereotype.Service;

import com.profile.entity.Certificate;
import com.profile.repository.CertificateRepository;

@Service
public class CertificateService {

    private final CertificateRepository repo;

    public CertificateService(CertificateRepository repo) {
        this.repo = repo;
    }

    public Certificate getCertificate(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    public Certificate addCertificate(Certificate request) {
        repo.findByUserId(request.getUserId()).ifPresent(c -> {
            throw new RuntimeException("Certificate already exists, use update instead");
        });

        return repo.save(request);
    }

    public Certificate updateCertificate(Certificate request) {
        Certificate cert = repo.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Certificate not found, add first"));

        cert.setCertification(request.getCertification());
        cert.setProvider(request.getProvider());
        cert.setUrl(request.getUrl());
        cert.setCertID(request.getCertID());
        cert.setIssuedDate(request.getIssuedDate());
        cert.setExpDate(request.getExpDate());
        cert.setDescription(request.getDescription());

        return repo.save(cert);
    }

    public String deleteCertificate(Long userId) {
        Certificate cert = repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        repo.delete(cert);
        return "Certificate deleted successfully";
    }
}
