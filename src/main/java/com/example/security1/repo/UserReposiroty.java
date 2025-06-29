package com.example.security1.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security1.entity.User;

public interface UserReposiroty extends JpaRepository<User, UUID>{

	public Optional<User> findByUserName(String un);
}
