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
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    public List<Product> getAllProductsWithConditions() {
        return productRepository.findByIsSaleTrueAndIsCheckTrueAndQuantityGreaterThan(0);
    }

    @Override
    public List<Product> searchProductsWithNameOrBrand(String nameOrBrand) {
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


    //后台管理
    private static final String UPLOAD_DIRECTORY = "D:/react-shoppingMall-picture/product-picture";

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProducts(String pname, String category, String brand, Boolean isSale, Boolean isCheck) {
        return productRepository.findAll((Specification<Product>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pname != null) {
                predicates.add(criteriaBuilder.like(root.get("pname"), "%" + pname + "%"));
            }
            if (category != null) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + category + "%"));
            }
            if (brand != null) {
                predicates.add(criteriaBuilder.like(root.get("brand"), "%" + brand + "%"));
            }
            if (isSale != null) {
                predicates.add(criteriaBuilder.equal(root.get("isSale"), isSale));
            }
            if (isCheck != null) {
                predicates.add(criteriaBuilder.equal(root.get("isCheck"), isCheck));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    public List<Product> addProduct(MultipartFile picture, String pname, String category, Double price,
                                    Boolean isSale, Integer quantity, String description, String service,
                                    String brand, String productType) {
        try {
            byte[] bytes = picture.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + "/" + picture.getOriginalFilename());
            Files.write(path, bytes);

            Product product = new Product();
            product.setPname(pname);
            product.setCategory(category);
            product.setPrice(price);
            product.setIsSale(isSale);
            product.setQuantity(quantity);
            product.setIsCheck(false);
            product.setPicturePath(path.toString());
            product.setDescription(description);
            product.setService(service);
            product.setIsRecommand(false);
            product.setBrand(brand);
            product.setProductType(productType);

            productRepository.save(product);
            return productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 或者根据需要处理错误
        }
    }

    @Override
    public List<Product> changeSale(Integer pid, Boolean isSale) {
        Optional<Product> productOptional = productRepository.findById(pid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setIsSale(isSale);
            productRepository.save(product);
        }
        return productRepository.findAll();
    }
}


