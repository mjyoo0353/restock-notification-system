package com.hanghae.restocknotificationsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //상품명

    @Column(nullable = false)
    private int restockCount; //재입고 회차

    @Column(nullable = false)
    private boolean isStock; //재고 상태

    private int quantity; //재고 수량

}


