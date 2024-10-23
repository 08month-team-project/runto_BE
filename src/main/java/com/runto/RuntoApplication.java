package com.runto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RuntoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuntoApplication.class, args);
    }

}
