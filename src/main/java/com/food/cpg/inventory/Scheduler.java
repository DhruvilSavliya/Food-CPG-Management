package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.PersistenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class Scheduler {

    @Autowired
    MailSenderUtil mailSender;

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void inventoryCheck() {

        List<RawMaterialInventory> rawMaterialInventoryDefaulterList = new ArrayList<>();
        rawMaterialInventoryDefaulterList = getPersistence().getDefaulter();
        if (rawMaterialInventoryDefaulterList.size() > 0) {
            for (RawMaterialInventory rawMaterialInventory : rawMaterialInventoryDefaulterList) {
                String message = "Raw Material- " + rawMaterialInventory.getRawMaterialName() + " from vendor- " + rawMaterialInventory.getVendorName() + " is less than the reorder point. A new order has been created!!";
                String to = rawMaterialInventory.getManufacturerEmail();
                String subject = "Inventory is low for- " + rawMaterialInventory.getRawMaterialName();
                System.out.println("Sending mail to- " + rawMaterialInventory.getManufacturerEmail());

                System.out.println(message);
                mailSender.sendEmail(to, subject, message);

            }
        }

    }

    private IRawMaterialInventoryPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialInventoryPersistence();
    }

}
