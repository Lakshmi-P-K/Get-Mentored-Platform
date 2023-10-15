package com.nineleaps.authentication.jwt.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.entity.RefreshToken;
import com.nineleaps.authentication.jwt.repository.RefreshTokenRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;


@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

	    public RefreshToken createRefreshToken(String emailId) {
	        RefreshToken refreshToken = new RefreshToken.RefreshTokenBuilder()
	        	
	                .user(userRepository.findByUserMail(emailId))
	                .token(UUID.randomUUID().toString())
	                .expiryDate(Instant.now().plusMillis(172800000)) // 2 days
	                .build();
	        
	        return refreshTokenRepository.save(refreshToken);
	    }


	    public Optional<RefreshToken> findByToken(String token){
	        return refreshTokenRepository.findByToken(token);
	    }
	    
	    public Optional<RefreshToken> verifyExpiration(RefreshToken refreshToken) {
		        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
		            refreshTokenRepository.delete(refreshToken);
		            return Optional.empty();
		        }
	        return Optional.of(refreshToken);
	    }

}
