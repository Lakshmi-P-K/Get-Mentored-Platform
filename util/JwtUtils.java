package com.nineleaps.authentication.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.nineleaps.authentication.jwt.entity.CustomUserDetails;
import com.nineleaps.authentication.jwt.service.JwtUserDetailsService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
@Component
public class JwtUtils {
	

	 
	 private static final String SECRET_KEY = "HRlELXqpSBssiieeeaqwertyujhgfdszxcASWERFDXCDWqwertyggffAQWSXCFRFFFTYHJkkitrsaafarsrAASSDFQWERQWASDWESDYUNJXCVRTYWSXCDERFVTYHJUK";

		 public String generateToken(UserDetails userDetails, HttpServletResponse response) throws IOException {
			    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
			    long now = System.currentTimeMillis();
			    Date validity = new Date(now + (60 * 60 * 1000));
			    List<String> roles = userDetails.getAuthorities().stream()
			            .map(GrantedAuthority::getAuthority)
			            .collect(Collectors.toList());
			    Long userId = null;
			    if (userDetails instanceof CustomUserDetails) {
			        userId = ((CustomUserDetails) userDetails).getUserId(); // Cast UserDetails to User and get user ID
			    }
			    String rolesString = String.join(",", roles);
			
			    String jwtToken = JWT.create()
			            .withSubject(userDetails.getUsername())
			            .withClaim("roles", rolesString)
			            .withClaim("userId", userId) // Add user ID claim
			            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 *10))
			            .sign(algorithm);
			    return jwtToken;
		}
	
		 public String extractSsoUserIdentifier(String token) {
			 Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			 return claims.getSubject();
		 }
	
		public String extractEmail(String token) {
		    return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
		}
	
	    public String getUserMailFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }
	    public String extractPhoneNumber(String token) {
	        return getClaimFromToken(token, "phoneNumber");
	    }
	
	    private String getClaimFromToken(String token, String claimName) {
	        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
	        return claims.get(claimName, String.class);
	    }
	
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }
	
	    public Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }
	   
	    public Boolean validateToken(String token) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
	            JWTVerifier verifier = JWT.require(algorithm).build();
	            DecodedJWT decodedJWT = verifier.verify(token);
	            System.out.println("validated");
	            // Perform any additional validation checks if required
	            return (decodedJWT != null && !isTokenExpired(decodedJWT));
	        } catch (JWTVerificationException exception) {
	            // Token verification failed
	            exception.printStackTrace(); // Print the exception stack trace for debugging
	            return false;
	        }
	    }
	
	    private Boolean isTokenExpired(DecodedJWT decodedJWT) {
	        Date expiration = decodedJWT.getExpiresAt();
	        return expiration != null && expiration.before(new Date());
	    }
	
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }
	
	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

    }



    


