package com.revconnect.service;
import com.revconnect.dao.NotificationDao;
import com.revconnect.model.Notification;

import java.util.List;

public class NotificationService {

    private final NotificationDao dao = new NotificationDao();

    public void createNotification(int userId, String message,String type) {
        dao.createNotification(userId, message, type);
    }

    public List<Notification> getUserNotifications(int userId) {

        return dao.getUserNotifications(userId);
    }

    public void markAllAsRead(int userId) {

        dao.markAllAsRead(userId);
    }
}
