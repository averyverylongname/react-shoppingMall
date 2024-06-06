package com.example.reactshoppingmall.service.Impl;

import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.entity.dto.ShoppingCar;
import com.example.reactshoppingmall.entity.dto.SubShoppingCar;
import com.example.reactshoppingmall.entity.dto.User;
import com.example.reactshoppingmall.repository.ProductRepository;
import com.example.reactshoppingmall.repository.ShoppingCarRepository;
import com.example.reactshoppingmall.repository.SubShoppingCarRepository;
import com.example.reactshoppingmall.repository.UserRepository;
import com.example.reactshoppingmall.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCarServiceImpl implements ShoppingCarService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;

    @Autowired
    private SubShoppingCarRepository subShoppingCarRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<SubShoppingCar> getShoppingCarDetails(Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return subShoppingCarRepository.findByUser(user);
    }

    @Override
    public void chooseProductInShoppingCar(Integer pid, Boolean isChoose, Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        SubShoppingCar subShoppingCar = subShoppingCarRepository.findByUserAndProduct(user, product);
        if (subShoppingCar != null) {
            subShoppingCar.setIsChoose(isChoose);
            subShoppingCarRepository.save(subShoppingCar);
            updateShoppingCarTotalPrice(subShoppingCar.getShoppingCar());
        }
    }

    @Override
    public void changeProductQuantityInShoppingCar(Integer pid, Integer quantity, Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        SubShoppingCar subShoppingCar = subShoppingCarRepository.findByUserAndProduct(user, product);
        if (subShoppingCar != null) {
            subShoppingCar.setQuantity(quantity);
            subShoppingCar.setPrice(product.getPrice() * quantity);
            subShoppingCarRepository.save(subShoppingCar);
            updateShoppingCarTotalPrice(subShoppingCar.getShoppingCar());
        }
    }

    @Override
    @Transactional
    public void clearShoppingCar(Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        ShoppingCar shoppingCar = shoppingCarRepository.findByUser(user);
        if (shoppingCar != null) {
            subShoppingCarRepository.deleteByShoppingCar(shoppingCar);
            shoppingCar.setTotalPrice(0.0);
            shoppingCarRepository.save(shoppingCar);
        }
    }

    @Override
    public void updateShoppingCarTotalPrice(ShoppingCar shoppingCar) {
        List<SubShoppingCar> subShoppingCars = subShoppingCarRepository.findByShoppingCarAndIsChooseTrue(shoppingCar);
        double totalPrice = subShoppingCars.stream().mapToDouble(SubShoppingCar::getPrice).sum();
        shoppingCar.setTotalPrice(totalPrice);
        shoppingCarRepository.save(shoppingCar);
    }
}
