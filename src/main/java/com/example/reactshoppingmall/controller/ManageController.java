package com.example.reactshoppingmall.controller;

import com.example.reactshoppingmall.entity.RestBean;
import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/manage/products")
public class ManageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public String getAllProducts() {
        return RestBean.success(productService.getAllProducts()).asJsonString();
    }

    @PostMapping("/search")
    public String searchProducts(
            @RequestParam(required = false) String pname,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Boolean is_sale,
            @RequestParam(required = false) Boolean is_check) {

        return RestBean.success(productService.searchProducts(pname, category, brand, is_sale, is_check)).asJsonString();
    }

    @PostMapping("/addOne")
    public String addProduct(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("pname") String pname,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam("is_sale") Boolean is_sale,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("service") String service,
            @RequestParam("brand") String brand,
            @RequestParam("productType") String productType) {

        return RestBean.success(productService.addProduct(picture, pname, category, price,
                is_sale, quantity, description, service, brand, productType)).asJsonString();
    }

    @PutMapping("/changeSale")
    public String changeSale(@RequestParam("pid") Integer pid, @RequestParam("isSale") Boolean isSale) {
        return RestBean.success(productService.changeSale(pid, isSale)).asJsonString();
    }
}