package com.project.conferencespringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.conferencespringdemo.repositories")
@EntityScan(basePackages = "com.project.conferencespringdemo.models")
public class ConferenceSpringDemoApplication {

    public static void main(String[] args) {
        // main entry point for Spring boot app
        SpringApplication.run(ConferenceSpringDemoApplication.class, args);
    }

}
