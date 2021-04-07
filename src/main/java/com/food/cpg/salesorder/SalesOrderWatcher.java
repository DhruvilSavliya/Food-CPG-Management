package com.food.cpg.salesorder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;

@Component
public class SalesOrderWatcher {

    private static final int DUE_DAY_FOR_PAYMENT = 7;
    private static final String SALES_ORDER_PAYMENT_OVERDUE_MESSAGE = "Sales order %s payment due date was %s.";

    @Scheduled(cron = "0 0 0 * * *")
    public void checkSalesOrdersForDueDate() {
        List<IManufacturer> manufacturers = getManufacturerPersistence().getAll();

        for (IManufacturer manufacturer : manufacturers) {
            checkSalesOrdersForDueDateByManufacturer(manufacturer.getId());
        }
    }

    public void checkSalesOrdersForDueDateByManufacturer(int manufacturerId) {
        List<SalesOrder> salesOrders = getSalesOrderPersistence().getAllShippedOrders(manufacturerId);

        for (SalesOrder salesOrder : salesOrders) {
            if (isSalesOrderPaymentOverdue(salesOrder)) {
                Date salesOrderDueDate = calculateSalesOrderDueDate(salesOrder);
                String notificationContent = String.format(SALES_ORDER_PAYMENT_OVERDUE_MESSAGE, salesOrder.getOrderNumber(), salesOrderDueDate);

                INotification notification = NotificationFactory.instance().makeNotification(manufacturerId, notificationContent, Timestamp.from(Instant.now()));
                notification.send();
            }
        }
    }

    public boolean isSalesOrderPaymentOverdue(SalesOrder salesOrder) {
        Date salesOrderDueDate = calculateSalesOrderDueDate(salesOrder);
        Date currentDate = new Date();

        if (currentDate.compareTo(salesOrderDueDate) > 0) {
            return true;
        }
        return false;
    }

    public Date calculateSalesOrderDueDate(SalesOrder salesOrder) {
        Timestamp salesOrderShippedTimestamp = salesOrder.getStatusChangeDate();

        Calendar salesOrderDueDate = Calendar.getInstance();
        salesOrderDueDate.setTime(salesOrderShippedTimestamp);
        salesOrderDueDate.add(Calendar.DATE, DUE_DAY_FOR_PAYMENT);

        return salesOrderDueDate.getTime();
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getSalesOrderPersistence();
    }

    private IManufacturerPersistence getManufacturerPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufacturerPersistence();
    }
}
