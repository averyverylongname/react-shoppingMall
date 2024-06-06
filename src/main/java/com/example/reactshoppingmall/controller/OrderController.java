package com.example.reactshoppingmall.controller;

import com.example.reactshoppingmall.entity.RestBean;
import com.example.reactshoppingmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam Integer uid) {
        return RestBean.success(orderService.createOrder(uid)).asJsonString();
    }

    @PutMapping("/submitOrder")
    public String submitOrder(@RequestParam String oid, @RequestParam String note) {
        orderService.submitOrder(oid, note);
        return RestBean.success().asJsonString();
    }

    @PutMapping("/verify")
    public String verifyOrder(@RequestParam String oid, @RequestParam String payMethod) {
        orderService.verifyOrder(oid, payMethod);
        return RestBean.success().asJsonString();
    }

    @GetMapping("/getOrder/{oid}")
    public String getOrder(@PathVariable String oid) {
        return RestBean.success(orderService.getOrderWithSubOrders(oid)).asJsonString();
    }
}
