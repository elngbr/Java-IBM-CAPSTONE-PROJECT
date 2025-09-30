package com.smartclinic.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for JWT token operations.
 * Handles token generation, validation, and extraction of claims.
 * Required for authentication and authorization in the Smart Clinic Management System.
 */
@Service
public class TokenService {

    @Value("${jwt.secret:mySecretKey}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extract username from JWT token.
     * Used to identify the user from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT token.
     * Used to check if token is expired.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract specific claim from JWT token.
     * Generic method to extract any claim from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token.
     * Used internally for claim extraction.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if JWT token is expired.
     * Used for token validation.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generate JWT token for user.
     * Creates a new token with username and role claims.
     */
    public String generateToken(String username, String role, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);
        return createToken(claims, username);
    }

    /**
     * Create JWT token with claims.
     * Internal method for token creation.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate JWT token.
     * Checks if token is valid for the given username and not expired.
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extract user role from JWT token.
     * Used for role-based access control.
     */
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    /**
     * Extract user ID from JWT token.
     * Used to identify the specific user entity.
     */
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    /**
     * Check if token is valid (not expired and properly formatted).
     * General token validation method.
     */
    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Refresh JWT token.
     * Creates a new token with updated expiration time.
     */
    public String refreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            Long userId = claims.get("userId", Long.class);
            
            return generateToken(username, role, userId);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid token for refresh");
        }
    }

    /**
     * Get remaining token validity time in milliseconds.
     * Used to inform clients about token expiration.
     */
    public Long getTokenValidityTime(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.getTime() - System.currentTimeMillis();
        } catch (JwtException | IllegalArgumentException e) {
            return 0L;
        }
    }

    /**
     * Generate token for admin user.
     * Specific method for admin authentication.
     */
    public String generateAdminToken(String username, Long adminId) {
        return generateToken(username, "ADMIN", adminId);
    }

    /**
     * Generate token for doctor user.
     * Specific method for doctor authentication.
     */
    public String generateDoctorToken(String email, Long doctorId) {
        return generateToken(email, "DOCTOR", doctorId);
    }

    /**
     * Generate token for patient user.
     * Specific method for patient authentication.
     */
    public String generatePatientToken(String email, Long patientId) {
        return generateToken(email, "PATIENT", patientId);
    }
}
