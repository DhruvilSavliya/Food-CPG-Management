package com.food.cpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class
 *
 * @author Kartik Gevariya
 */
@SpringBootApplication
public class FoodCPGApplication {

    private static final Logger LOG = LoggerFactory.getLogger(FoodCPGApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FoodCPGApplication.class, args);
        LOG.info("Food CPG Application Started Successfully.");
    }
}