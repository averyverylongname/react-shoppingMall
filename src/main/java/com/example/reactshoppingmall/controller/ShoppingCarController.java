package com.example.reactshoppingmall.controller;

import com.example.reactshoppingmall.entity.RestBean;
import com.example.reactshoppingmall.entity.dto.SubShoppingCar;
import com.example.reactshoppingmall.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCar")
public class ShoppingCarController {

    @Autowired
    private ShoppingCarService shoppingCarService;

    @GetMapping("/getAll/{uid}")
    public String getShoppingCarDetails(@PathVariable Integer uid) {
        return RestBean.success(shoppingCarService.getShoppingCarDetails(uid)).asJsonString();
    }

    @PostMapping("/choose")
    public String chooseProductInShoppingCar(@RequestParam Integer pid, @RequestParam Boolean isChoose, @RequestParam Integer uid) {
        shoppingCarService.chooseProductInShoppingCar(pid, isChoose, uid);
        return RestBean.success().asJsonString();
    }

    @PostMapping("/changeQuantity")
    public String changeProductQuantityInShoppingCar(@RequestParam Integer pid, @RequestParam Integer quantity, @RequestParam Integer uid) {
        shoppingCarService.changeProductQuantityInShoppingCar(pid, quantity, uid);
        return RestBean.success().asJsonString();
    }

    @DeleteMapping("/delete")
    public String clearShoppingCar(@RequestParam Integer uid) {
        shoppingCarService.clearShoppingCar(uid);
        return RestBean.success().asJsonString();
    }
}
