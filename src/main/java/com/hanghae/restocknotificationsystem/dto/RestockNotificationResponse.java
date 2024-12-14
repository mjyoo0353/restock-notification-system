package com.hanghae.restocknotificationsystem.dto;

import com.hanghae.restocknotificationsystem.NotificationStatus;
import com.hanghae.restocknotificationsystem.entity.ProductNotificationHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestockNotificationResponse {
    //재입고 알림 전송 히스토리 정보 반환

    private Long id;
    private Long productId;
    private int restockCount; //재입고 회차
    private NotificationStatus status; //재입고 알림 발송 상태
    private Long lastSentUserId; //마지막 발송 유저 아이디

    public RestockNotificationResponse(ProductNotificationHistory history) {
        this.id = history.getId();
        this.productId = history.getProductId();
        this.restockCount = history.getRestockCount();
        this.status = history.getStatus();
        this.lastSentUserId = history.getLastUserId();
    }
}
