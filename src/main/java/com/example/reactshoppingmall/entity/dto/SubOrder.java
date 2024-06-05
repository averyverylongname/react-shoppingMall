package com.example.reactshoppingmall.entity.dto;

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
@Table(name = "sub_order")
public class SubOrder {
    @Id
    @Column(name = "soid")
    private String soid; // 需要在生成实例时通过当前时间生成唯一id

    @Column(name = "time")
    private Date time;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "oid", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product;
}
