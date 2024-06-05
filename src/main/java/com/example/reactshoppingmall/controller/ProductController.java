package com.example.reactshoppingmall.controller;

import com.example.reactshoppingmall.entity.RestBean;
import com.example.reactshoppingmall.entity.dto.Product;
import com.example.reactshoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mall/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //全部商品
    //要求已检查、已上架、数量大于0
    @GetMapping("/getAll")
    public String getAllProducts() {
        return RestBean.success(productService.getAllProducts()).asJsonString();
    }

    //搜索商品
    //商品名or品牌名模糊匹配
    //要求已检查、已上架、数量大于0
    @GetMapping("/search/{nameOrBrand}")
    public String searchProducts(@PathVariable String nameOrBrand) {
        return RestBean.success(productService.searchProducts(nameOrBrand)).asJsonString();
    }

    //商品分类
    //类别完全匹配
    //要求已检查、已上架、数量大于0
    @GetMapping("/category/{category}")
    public String getProductsByCategory(@PathVariable String category) {
        return RestBean.success(productService.getProductsByCategory(category)).asJsonString();
    }

    //商品详情
    //指定pid商品
    @GetMapping("/detail/{pid}")
    public String getProductDetail(@PathVariable Integer pid) {
        return RestBean.success(productService.getProductDetail(pid)).asJsonString();
    }
}
