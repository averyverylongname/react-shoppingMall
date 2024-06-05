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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Integer pid;

    @Column(name = "pname")
    private String pname;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "category")
    private String category;

    @Column(name = "picture_path")
    private String picturePath;

    @Column(name = "brand")
    private String brand;

    @Column(name = "is_sale")
    private Boolean isSale;

    @Column(name = "is_recommand")
    private Boolean isRecommand;

    @Column(name = "is_check")
    private Boolean isCheck;

    @Column(name = "description")
    private String description;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "service")
    private String service;
}
