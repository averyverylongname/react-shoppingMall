package com.example.reactshoppingmall.repository;

import com.example.reactshoppingmall.entity.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
