package com.hanghae.restocknotificationsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductUserNotificationHistory {
    //상품과 유저별로 알림을 보낸 히스토리 기록

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private int restockCount; //재입고 회차
    private LocalDateTime sentDate = LocalDateTime.now(); //알림 발송 날짜

    public ProductUserNotificationHistory(Long productId, Long userId, int restockCount) {
        this.productId = productId;
        this.userId = userId;
        this.restockCount = restockCount;
        this.sentDate = LocalDateTime.now();
    }
}
