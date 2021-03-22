package com.food.cpg.inventory;

import com.food.cpg.authentication.AuthenticationSessionDetails;

import com.food.cpg.databasepersistence.PersistenceFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


@Component
@ConditionalOnProperty(name= "scheduling.enabled", matchIfMissing = true)
public class Scheduler{

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void inventoryCheck(){

        List<RawMaterialInventory> rawMaterialInventoryDefaulterList = new ArrayList<>();
        int loggedInManufacturerId = getLoggedInManufacturerId();
        rawMaterialInventoryDefaulterList = getPersistence().getDefaulter();
        if (rawMaterialInventoryDefaulterList.size() > 0){
            for (RawMaterialInventory rawMaterialInventory : rawMaterialInventoryDefaulterList){
                String message = "Raw Material- " + rawMaterialInventory.getRawMaterialName() + " from vendor- " + rawMaterialInventory.getVendorName() + " is less than the reorder point. Please create new purchase order!!";
                System.out.println(message);
            }
        }

    }

    private IRawMaterialInventoryPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialInventoryPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

}
