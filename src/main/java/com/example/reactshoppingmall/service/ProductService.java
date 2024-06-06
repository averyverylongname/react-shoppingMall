package com.example.reactshoppingmall.service;

import com.example.reactshoppingmall.entity.dto.Product;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductService {
    List<Product> getAllProductsWithConditions();
    List<Product> searchProductsWithNameOrBrand(String nameOrBrand);
    List<Product> getProductsByCategory(String category);
    Product getProductDetail(Integer pid);
    void addShoppingCar(Integer pid, Integer uid);

    //后台管理

    List<Product> getAllProducts();
    List<Product> searchProducts(String pname, String category, String brand, Boolean isSale, Boolean isCheck);
    List<Product> addProduct(MultipartFile picture, String pname, String category,
                             Double price, Boolean isSale, Integer quantity,
                             String description, String service, String brand,
                             String productType);
    List<Product> changeSale(Integer pid, Boolean isSale);
}
