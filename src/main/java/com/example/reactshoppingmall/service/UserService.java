package com.example.reactshoppingmall.service;

import com.example.reactshoppingmall.entity.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //登录，校验身份（user, admin, product, order, sale）
    //返回user，失败时返回null
    User login(String username, String password, boolean isAdmin);
    User getUserById(Integer uid);
}
