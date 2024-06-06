package com.example.reactshoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ReactShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactShoppingMallApplication.class, args);
    }

}
