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
public class ProductUserNotification {
    //상품별 재입고 알림을 설정한 유저 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private boolean isActive; //알림 설정 활성화 여부 (true: 활성화, false: 비활성화)
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
