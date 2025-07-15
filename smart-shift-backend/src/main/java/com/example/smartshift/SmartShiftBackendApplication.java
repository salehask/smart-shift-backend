package com.example.smartshift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point of the Smart Shift Backend Spring Boot Application.
 */
@SpringBootApplication
@EnableScheduling
public class SmartShiftBackendApplication {

    /**
     * Application main method.
     * @param args application arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartShiftBackendApplication.class, args);
    }
}