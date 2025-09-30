package com.smartclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main application class for Smart Clinic Management System
 * Enables JPA repositories with basic configuration
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartclinic.repository")
public class SmartClinicManagementApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartClinicManagementApplication.class, args);
    }
}
