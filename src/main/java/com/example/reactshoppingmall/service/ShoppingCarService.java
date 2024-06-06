package com.example.reactshoppingmall.service;

import com.example.reactshoppingmall.entity.dto.SubShoppingCar;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ShoppingCarService {
    List<SubShoppingCar> getShoppingCarDetails(Integer uid);
    void chooseProductInShoppingCar(Integer pid, Boolean isChoose, Integer uid);
    void changeProductQuantityInShoppingCar(Integer pid, Integer quantity, Integer uid);
    void clearShoppingCar(Integer uid);
}
