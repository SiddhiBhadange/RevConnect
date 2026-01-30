package com.revconnect.service;

import com.revconnect.dao.UserDao;
import com.revconnect.model.User;

public class UserService {
    private final UserDao dao = new UserDao();


    public boolean register(User u) {

        return dao.register(u);
    }


    public User login(String input, String password) {

        return dao.login(input, password);
    }
}
