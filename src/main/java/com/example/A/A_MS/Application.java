package com.example.A.A_MS; // This is the root package

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // This line launches the entire Spring framework
        SpringApplication.run(Application.class, args);
    }
}