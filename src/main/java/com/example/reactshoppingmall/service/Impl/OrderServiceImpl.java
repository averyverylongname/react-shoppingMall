package com.example.reactshoppingmall.service.Impl;

import com.example.reactshoppingmall.entity.dto.*;
import com.example.reactshoppingmall.repository.*;
import com.example.reactshoppingmall.service.OrderService;
import com.example.reactshoppingmall.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;

    @Autowired
    private SubShoppingCarRepository subShoppingCarRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SubOrderRepository subOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCarService shoppingCarService;

    @Override
    @Transactional
    public List<SubOrder> createOrder(Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        ShoppingCar shoppingCar = shoppingCarRepository.findByUser(user);
        if (shoppingCar == null) {
            throw new IllegalArgumentException("Shopping car is empty");
        }

        List<SubShoppingCar> subShoppingCars = subShoppingCarRepository.findByShoppingCarAndIsChooseTrue(shoppingCar);
        if (subShoppingCars.isEmpty()) {
            throw new IllegalArgumentException("No items in shopping car");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String oidPrefix = sdf.format(new Date());
        String oid = oidPrefix + UUID.randomUUID().toString();

        Order order = new Order()
                .setOid(oid)
                .setTime(new Date())
                .setNote(null)
                .setPayMethod(null)
                .setUser(user);

        orderRepository.save(order);

        double totalPrice = 0.0;
        List<SubOrder> subOrders = new ArrayList<>();

        for (SubShoppingCar subShoppingCar : subShoppingCars) {
            String soid = oidPrefix + UUID.randomUUID().toString();
            SubOrder subOrder = new SubOrder()
                    .setSoid(soid)
                    .setTime(new Date())
                    .setQuantity(subShoppingCar.getQuantity())
                    .setPrice(subShoppingCar.getPrice())
                    .setUser(user)
                    .setOrder(order)
                    .setProduct(subShoppingCar.getProduct());

            // 更新产品数量
            Product product = subShoppingCar.getProduct();
            int newQuantity = product.getQuantity() - subShoppingCar.getQuantity();
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Product quantity is not sufficient");
            }
            product.setQuantity(newQuantity);
            productRepository.save(product);

            subOrderRepository.save(subOrder);
            totalPrice += subOrder.getPrice();
            subOrders.add(subOrder);
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Clear chosen items in shopping car after creating the order
        for (SubShoppingCar subShoppingCar : subShoppingCars) {
            subShoppingCarRepository.delete(subShoppingCar);
        }

        shoppingCarService.updateShoppingCarTotalPrice(shoppingCar);

        return subOrders;
    }


    @Override
    @Transactional
    public void submitOrder(String oid, String note) {
        Order order = orderRepository.findById(oid).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setNote(note);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void verifyOrder(String oid, String payMethod) {
        Order order = orderRepository.findById(oid).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setPayMethod(payMethod);
        orderRepository.save(order);
    }

    @Override
    public List<SubOrder> getOrderWithSubOrders(String oid) {
        Order order = orderRepository.findById(oid).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return subOrderRepository.findByOrder(order);
    }


    @Override
    @Transactional
    public SubOrder buyProduct(Integer pid, Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() <= 0) {
            throw new IllegalArgumentException("Product quantity is not sufficient");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String oidPrefix = sdf.format(new Date());
        String oid = oidPrefix + UUID.randomUUID().toString();

        Order order = new Order()
                .setOid(oid)
                .setTime(new Date())
                .setNote(null)
                .setPayMethod(null)
                .setUser(user);

        orderRepository.save(order);

        String soid = oidPrefix + UUID.randomUUID().toString();
        SubOrder subOrder = new SubOrder()
                .setSoid(soid)
                .setTime(new Date())
                .setQuantity(1)
                .setPrice(product.getPrice())
                .setUser(user)
                .setOrder(order)
                .setProduct(product);

        subOrderRepository.save(subOrder);

        // 更新产品数量
        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);

        order.setTotalPrice(product.getPrice());
        orderRepository.save(order);

        return subOrder;
    }
}
