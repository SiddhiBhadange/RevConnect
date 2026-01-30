package com.revconnect.service;

import com.revconnect.dao.ConnectionDao;
import com.revconnect.dao.UserDao;

import java.util.List;

public class ConnectionService {

    private final ConnectionDao connectionDao = new ConnectionDao();
    private final UserDao userDao = new UserDao();

    public boolean sendRequest(int senderId, int receiverId) {
        if (!userDao.userExists(receiverId)) {
            System.out.println("User does not exist!");
            return false;
        }
        return connectionDao.sendRequest(senderId, receiverId);
    }

    public boolean updateStatus(int requestId, String status) {
        return connectionDao.updateStatus(requestId, status);
    }


    // View connections
    public List<String> getConnections(int userId) {
        return connectionDao.getConnections(userId);
    }

    public List<String> getpendingRequests(int userId) {
        return connectionDao.getPendingRequests(userId);
    }


    public boolean removeConnection(int id, int userIdToRemove) {
        return connectionDao.removeConnection(id, userIdToRemove);
    }
}
