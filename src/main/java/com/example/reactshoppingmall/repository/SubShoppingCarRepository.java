package com.example.reactshoppingmall.repository;

import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.entity.dto.ShoppingCar;
import com.example.reactshoppingmall.entity.dto.SubShoppingCar;
import com.example.reactshoppingmall.entity.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubShoppingCarRepository extends JpaRepository<SubShoppingCar, Integer> {
    List<SubShoppingCar> findByShoppingCarAndIsChooseTrue(ShoppingCar shoppingCar);
    SubShoppingCar findByProductAndUser(Product product, User user);
}

