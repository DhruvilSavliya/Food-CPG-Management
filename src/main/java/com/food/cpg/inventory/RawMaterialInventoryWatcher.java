package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.Manufacturer;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.IRawMaterialPersistence;
import com.food.cpg.rawmaterial.RawMaterial;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


@Component
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class RawMaterialInventoryWatcher {

    private static final String NEW_PURCHASE_ORDER_CREATION_MESSAGE = "Raw Material inventory for raw material(s) %s was low from vendor %s. A new purchase order- %s amounting- %s has been created.";
    private static final String COMMA = ",";

    @Scheduled(initialDelay = 30000, fixedDelay = 3000000)
    public void inventoryCheck() {
        List<Manufacturer> manufacturers = getManufacturerPersistence().getAll();

        for (Manufacturer manufacturer : manufacturers) {
            inventoryCheckForEachManufacturer(manufacturer.getId());
        }
    }

    public void inventoryCheckForEachManufacturer(int manufacturerId) {
        List<RawMaterial> rawMaterials = getRawMaterialPersistence().getAll(manufacturerId);
        List<IRawMaterialInventory> rawMaterialInventoryList = getRawMaterialInventoryPersistence().getAll(manufacturerId);

        Map<Integer, IRawMaterialInventory> rawMaterialInventoryMap = new HashMap<>();
        for (IRawMaterialInventory rawMaterialInventory : rawMaterialInventoryList){
            rawMaterialInventoryMap.put(rawMaterialInventory.getRawMaterialId(), rawMaterialInventory);
        }

        Map<Integer, List<RawMaterial>> rawMaterialMap = new HashMap<>();

        for (RawMaterial rawMaterial : rawMaterials) {
            IRawMaterialInventory rawMaterialInventory = rawMaterialInventoryMap.get(rawMaterial.getId());
            if (rawMaterialInventory.getRawMaterialQuantity() < rawMaterial.getReorderPointQuantity()) {
                if (rawMaterialMap.containsKey(rawMaterial.getVendorId())) {
                    List<RawMaterial> rawMaterialList = rawMaterialMap.get(rawMaterial.getVendorId());
                    rawMaterialList.add(rawMaterial);
                    rawMaterialMap.put(rawMaterial.getVendorId(), rawMaterialList);
                } else {
                    List<RawMaterial> rawMaterialList = new ArrayList<>();
                    rawMaterialList.add(rawMaterial);
                    rawMaterialMap.put(rawMaterial.getVendorId(), rawMaterialList);
                }
            }
        }

        for (Map.Entry<Integer, List<RawMaterial>> entry : rawMaterialMap.entrySet()) {
            createPurchaseOrder(entry.getKey(), entry.getValue(), manufacturerId);
        }
    }

    public void createPurchaseOrder(Integer vendorId, List<RawMaterial> rawMaterials, int manufacturerId) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setManufacturerId(manufacturerId);
        purchaseOrder.setVendorId(vendorId);

        for (RawMaterial rawMaterial : rawMaterials){
            PurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
            purchaseOrderRawMaterial.setRawMaterialId(rawMaterial.getId());
            purchaseOrderRawMaterial.setRawMaterialName(rawMaterial.getName());
            purchaseOrderRawMaterial.setRawMaterialQuantity(calculatePurchaseOrderRawMaterialQuantity(rawMaterial.getReorderPointQuantity()));
            purchaseOrderRawMaterial.setRawMaterialQuantityUOM(rawMaterial.getUnitMeasurementUOM());
            purchaseOrderRawMaterial.setRawMaterialCost(rawMaterial.getUnitCost());
            purchaseOrderRawMaterial.loadDetails(rawMaterial);
            purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
            getPurchaseOrderRawMaterialPersistence().save(purchaseOrderRawMaterial);

        }

        getPurchaseOrderPersistence().save(purchaseOrder);
        sendNotification(purchaseOrder, manufacturerId);
    }

    public double calculatePurchaseOrderRawMaterialQuantity(Double reOrderPoint){
        Double rawMaterialQuantity = reOrderPoint * 5;
        return rawMaterialQuantity;
    }

    public void sendNotification(PurchaseOrder purchaseOrder,int manufacturerId){

        StringJoiner rawMaterialNameJoiner = new StringJoiner(COMMA);

        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrder.getPurchaseOrderRawMaterials()){
            rawMaterialNameJoiner.add(purchaseOrderRawMaterial.getRawMaterialName());
        }

        String notificationContent = String.format(NEW_PURCHASE_ORDER_CREATION_MESSAGE, rawMaterialNameJoiner.toString(), purchaseOrder.getVendorId() ,purchaseOrder.getOrderNumber(), purchaseOrder.getTotalCost());
        INotification notification = NotificationFactory.instance().makeNotification(manufacturerId, notificationContent, Timestamp.from(Instant.now()));
        notification.send();
    }

    private IRawMaterialInventoryPersistence getRawMaterialInventoryPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialInventoryPersistence();
    }

    private IManufacturerPersistence getManufacturerPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufacturerPersistence();
    }

    private IRawMaterialPersistence getRawMaterialPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialPersistence();
    }

    private IPurchaseOrderPersistence getPurchaseOrderPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
    }

    private IPurchaseOrderRawMaterialPersistence getPurchaseOrderRawMaterialPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderRawMaterialPersistence();
    }

}
