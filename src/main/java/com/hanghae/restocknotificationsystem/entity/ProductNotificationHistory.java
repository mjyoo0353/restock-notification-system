package com.hanghae.restocknotificationsystem.entity;

import com.hanghae.restocknotificationsystem.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductNotificationHistory {
    //상품별 재입고 알림 전송 상태 기록 (알림 전송 했는지 기록)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int restockCount; //재입고 회차

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; //재입고 알림 발송 상태

    private Long lastUserId; //마지막 발송 유저 아이디

    public ProductNotificationHistory(Long productId, int restockCount, NotificationStatus notificationStatus) {
        this.productId = productId;
        this.restockCount = restockCount;
        this.status = notificationStatus;
    }
}
