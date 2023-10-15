package com.nineleaps.authentication.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nineleaps.authentication.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Integer>{

    Optional<RefreshToken> findByToken(String token);
}
