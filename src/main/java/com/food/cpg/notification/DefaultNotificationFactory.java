package com.food.cpg.notification;

import java.sql.Timestamp;

import com.food.cpg.applicationhandlers.ApplicationBeanHandler;
import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class DefaultNotificationFactory extends NotificationFactory {

    ICommonDatabaseOperation commonDatabaseOperation = ApplicationBeanHandler.getBean(ICommonDatabaseOperation.class);

    private final INotificationPersistence notificationPersistence;

    public DefaultNotificationFactory() {
        notificationPersistence = new NotificationDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public INotification makeNotification(int userId, String content, Timestamp notificationDate) {
        return new Notification(userId, content, notificationDate);
    }

    @Override
    public INotificationPersistence makeNotificationPersistence() {
        return notificationPersistence;
    }
}
