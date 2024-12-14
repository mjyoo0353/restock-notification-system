package com.hanghae.restocknotificationsystem.controller;

import com.hanghae.restocknotificationsystem.dto.RestockNotificationResponse;
import com.hanghae.restocknotificationsystem.service.ProductNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductNotificationController {

    private final ProductNotificationService productNotificationService;

    //재입고 알림을 설정한 유저들에게 재입고 알림 전송 API
    @PostMapping("/{productId}/notifications/restock")
    public RestockNotificationResponse sendRestockNotification(@PathVariable("productId") Long productId) {
        return productNotificationService.sendRestockNotification(productId);
    }

}
