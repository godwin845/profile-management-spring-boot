package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Social;
import com.profile.entity.User;

import java.util.List;

public interface SocialRepository extends JpaRepository<Social, Long> {

    List<Social> findByUser(User user);

    void deleteByUser(User user);
}
