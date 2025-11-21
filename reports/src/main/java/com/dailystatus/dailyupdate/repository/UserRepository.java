package com.dailystatus.dailyupdate.repository;

import com.dailystatus.dailyupdate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
