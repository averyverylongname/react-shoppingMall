package com.example.reactshoppingmall.controller;

import com.example.reactshoppingmall.entity.RestBean;
import com.example.reactshoppingmall.entity.dto.User;
import com.example.reactshoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //登录，校验身份（user, admin, product, order, sale）
    //返回user，失败时返回null
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam Boolean isAdmin) {
        User user = userService.login(username, password, isAdmin);
        if (user!=null){
            return RestBean.success(user).asJsonString();
        }else return RestBean.failure(400, "用户不存在").asJsonString();
    }
}