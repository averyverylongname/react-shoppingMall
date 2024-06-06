package com.example.reactshoppingmall.repository;

import com.example.reactshoppingmall.entity.dto.Order;
import com.example.reactshoppingmall.entity.dto.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, String> {
    List<SubOrder> findByOrder(Order order);
}
