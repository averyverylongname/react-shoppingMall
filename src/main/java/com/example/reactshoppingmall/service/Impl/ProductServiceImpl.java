package com.example.reactshoppingmall.service.Impl;

import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.entity.dto.ShoppingCar;
import com.example.reactshoppingmall.entity.dto.SubShoppingCar;
import com.example.reactshoppingmall.entity.dto.User;
import com.example.reactshoppingmall.repository.ProductRepository;
import com.example.reactshoppingmall.repository.ShoppingCarRepository;
import com.example.reactshoppingmall.repository.SubShoppingCarRepository;
import com.example.reactshoppingmall.repository.UserRepository;
import com.example.reactshoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;

    @Autowired
    private SubShoppingCarRepository subShoppingCarRepository;

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

    @Override
    public void addShoppingCar(Integer pid, Integer uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ShoppingCar shoppingCar = shoppingCarRepository.findByUser(user);
        if (shoppingCar == null) {
            shoppingCar = new ShoppingCar().setUser(user).setTotalPrice(0.0);
            shoppingCarRepository.save(shoppingCar);
        }

        SubShoppingCar subShoppingCar = subShoppingCarRepository.findByProductAndUser(product, user);
        if (subShoppingCar != null) {
            subShoppingCar.setQuantity(subShoppingCar.getQuantity() + 1);
            subShoppingCar.setPrice(subShoppingCar.getPrice() + product.getPrice());
        } else {
            subShoppingCar = new SubShoppingCar()
                    .setProduct(product)
                    .setQuantity(1)
                    .setPrice(product.getPrice())
                    .setIsChoose(true)
                    .setUser(user)
                    .setShoppingCar(shoppingCar);
        }
        subShoppingCarRepository.save(subShoppingCar);

        List<SubShoppingCar> subShoppingCars = subShoppingCarRepository.findByShoppingCarAndIsChooseTrue(shoppingCar);
        double totalPrice = subShoppingCars.stream().mapToDouble(SubShoppingCar::getPrice).sum();
        shoppingCar.setTotalPrice(totalPrice);
        shoppingCarRepository.save(shoppingCar);
    }

}
