package com.dovydasvenckus.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;

@EntityScan(
        basePackageClasses = {Jsr310Converters.class},
        basePackages = {"com.dovydasvenckus.budget"}
)
@SpringBootApplication
public class BudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetApplication.class, args);
    }
}
