package com.example.reactshoppingmall.repository;

import com.example.reactshoppingmall.entity.dto.ShoppingCar;
import com.example.reactshoppingmall.entity.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCar, Integer> {
    ShoppingCar findByUser(User user);
}
