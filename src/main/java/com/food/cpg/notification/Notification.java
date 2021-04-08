package com.food.cpg.notification;

import java.sql.Timestamp;
import java.util.List;

public class Notification implements INotification {
    private final int userId;
    private final String content;
    private final Timestamp notificationDate;

    public Notification(int userId, String content, Timestamp notificationDate) {
        this.userId = userId;
        this.content = content;
        this.notificationDate = notificationDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getNotificationDate() {
        return notificationDate;
    }

    public List<INotification> getAll() {
        return getPersistence().getAll(this.getUserId());
    }

    public void send() {
        getPersistence().send(this);
    }

    private INotificationPersistence getPersistence() {
        return NotificationFactory.instance().makeNotificationPersistence();
    }
}
