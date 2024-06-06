package com.example.reactshoppingmall.repository;

import com.example.reactshoppingmall.entity.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByPnameContainingOrBrandContainingAndIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(String name, String brand, Integer quantity);
    List<Product> findByCategoryAndIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(String category, Integer quantity);
    List<Product> findByIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(Integer quantity);
}
