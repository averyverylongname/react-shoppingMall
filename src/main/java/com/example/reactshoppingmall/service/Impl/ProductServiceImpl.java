package com.example.reactshoppingmall.service.Impl;

import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.repository.ProductRepository;
import com.example.reactshoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(0);
    }

    @Override
    public List<Product> searchProducts(String nameOrBrand) {
        return productRepository.findByPnameContainingOrBrandContainingAndIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(nameOrBrand, nameOrBrand, 0);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(category, 0);
    }

    @Override
    public Product getProductDetail(Integer pid) {
        return productRepository.findById(pid).orElse(null);
    }
}
