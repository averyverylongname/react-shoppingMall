package com.example.reactshoppingmall.service;

import com.example.reactshoppingmall.entity.dto.Order;
import com.example.reactshoppingmall.entity.dto.SubOrder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface OrderService {
    List<SubOrder> createOrder(Integer uid);
    void submitOrder(String oid, String note);
    void verifyOrder(String oid, String payMethod);
    List<SubOrder> getOrderWithSubOrders(String oid);
    SubOrder buyProduct(Integer pid, Integer uid);

}
