package com.linearalgebra.bot.service;

import com.linearalgebra.bot.dao.UserRepository;
import com.linearalgebra.bot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent())
            user = optional.get();
        return user;
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userRepository.getUserByChatId(chatId);
    }
}
