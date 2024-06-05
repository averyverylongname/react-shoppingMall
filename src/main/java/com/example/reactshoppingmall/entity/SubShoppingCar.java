package com.example.reactshoppingmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sub_shopping_car")
public class SubShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssid")
    private Integer ssid;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_choose")
    private Boolean isChoose;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sid", nullable = false)
    private ShoppingCar shoppingCar;
}
