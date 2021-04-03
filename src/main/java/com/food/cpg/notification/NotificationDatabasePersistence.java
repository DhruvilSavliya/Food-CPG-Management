package com.food.cpg.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class NotificationDatabasePersistence implements INotificationPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public NotificationDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<INotification> getAll(int userId) {
        List<INotification> notifications = new ArrayList<>();

        String sql = "select * from notifications where user_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(userId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String content = rs.getString("content");
                        Timestamp notificationDate = rs.getTimestamp("notification_date");

                        INotification notification = NotificationFactory.instance().makeNotification(userId, content, notificationDate);

                        notifications.add(notification);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return notifications;
    }

    @Override
    public void send(INotification notification) {
        String sql = "insert into notifications (user_id, content) values (?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(notification.getUserId());
        placeholderValues.add(notification.getContent());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}