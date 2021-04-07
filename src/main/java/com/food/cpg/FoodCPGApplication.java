package com.food.cpg;

import com.food.cpg.authentication.AuthenticationFactory;
import com.food.cpg.authentication.DefaultAuthenticationFactory;
import com.food.cpg.packaging.DefaultPackageFactory;
import com.food.cpg.packaging.PackageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.inventory.DefaultInventoryFactory;
import com.food.cpg.inventory.InventoryFactory;
import com.food.cpg.notification.DefaultNotificationFactory;
import com.food.cpg.notification.NotificationFactory;

@SpringBootApplication
@EnableScheduling
public class FoodCPGApplication {

    private static final Logger LOG = LoggerFactory.getLogger(FoodCPGApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FoodCPGApplication.class, args);
        configureFactories();
        LOG.info("Food CPG Application Started Successfully.");
    }

    private static void configureFactories() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();

        AuthenticationFactory.setAuthenticationFactory(new DefaultAuthenticationFactory());
        InventoryFactory.setInventoryFactory(new DefaultInventoryFactory(persistenceFactory));
        NotificationFactory.setNotificationFactory(new DefaultNotificationFactory(persistenceFactory));
        PackageFactory.setPackageFactory(new DefaultPackageFactory(persistenceFactory));
    }
}