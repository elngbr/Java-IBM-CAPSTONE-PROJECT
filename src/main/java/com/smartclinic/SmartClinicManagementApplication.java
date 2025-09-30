package com.smartclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Main application class for Smart Clinic Management System
 * Enables JPA and MongoDB repositories with auditing support
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartclinic.repository.jpa")
@EnableMongoRepositories(basePackages = "com.smartclinic.repository.mongodb")
@EnableJpaAuditing
@EnableMongoAuditing
public class SmartClinicManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartClinicManagementApplication.class, args);
    }
}
