package com.example.reactshoppingmall.service.Impl;

import com.example.reactshoppingmall.entity.dto.User;
import com.example.reactshoppingmall.repository.UserRepository;
import com.example.reactshoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password, boolean isAdmin) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        }

        if (!isAdmin && "user".equals(user.getRole())) {
            return user;
        }

        if (isAdmin && !"user".equals(user.getRole())) {
            return user;
        }

        return null;
    }
}
