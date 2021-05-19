package com.linearalgebra.bot.controller;

import com.linearalgebra.bot.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserService {
    private static List<User> users = new ArrayList<>();

    public void addUser(User user){
        users.add(user);
    }

    public User getUserById(int id){
        for(User user: users){
            if(user.getChatId()==id)
                return user;
        }
        return null;
    }
}
