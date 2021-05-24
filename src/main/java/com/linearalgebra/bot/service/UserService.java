package com.linearalgebra.bot.service;

import com.linearalgebra.bot.entity.User;

public interface UserService {
    void addUser(User user);
    User getUserById(Long id);
    User getUserByChatId(Long chatId);
}
