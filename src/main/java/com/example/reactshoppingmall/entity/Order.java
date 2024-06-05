package com.example.reactshoppingmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "orders") // 'order' 是 SQL 保留字，需要使用其他名称
public class Order {
    @Id
    @Column(name = "oid")
    private String oid; // 需要在生成实例时通过当前时间生成唯一id

    @Column(name = "time")
    private Date time;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "pay_method")
    private String payMethod;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;
}
