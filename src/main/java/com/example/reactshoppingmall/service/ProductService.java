package com.example.reactshoppingmall.service;

import com.example.reactshoppingmall.entity.dto.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    List<Product> searchProducts(String nameOrBrand);
    List<Product> getProductsByCategory(String category);
    Product getProductDetail(Integer pid);
}
