package com.hanghae.restocknotificationsystem.service;

import com.hanghae.restocknotificationsystem.enums.NotificationStatus;
import com.hanghae.restocknotificationsystem.dto.RestockNotificationResponse;
import com.hanghae.restocknotificationsystem.entity.Product;
import com.hanghae.restocknotificationsystem.entity.ProductNotificationHistory;
import com.hanghae.restocknotificationsystem.entity.ProductUserNotification;
import com.hanghae.restocknotificationsystem.entity.ProductUserNotificationHistory;
import com.hanghae.restocknotificationsystem.repository.ProductNotificationHistoryRepository;
import com.hanghae.restocknotificationsystem.repository.ProductRepository;
import com.hanghae.restocknotificationsystem.repository.ProductUserNotificationHistoryRepository;
import com.hanghae.restocknotificationsystem.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductNotificationService {

    private final ProductRepository productRepo;
    private final ProductUserNotificationRepository userNotificationRepo;
    private final ProductUserNotificationHistoryRepository userNotificationHistoryRepo;
    private final ProductNotificationHistoryRepository notificationHistoryRepo;

    //재입고 알림을 설정한 유저들에게 재입고 알림 전송
    public RestockNotificationResponse sendRestockNotification(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        //재입고 알림을 전송하기 전, 상품의 재입고 회차를 1 증가시킴
        product.setRestockCount(product.getRestockCount() + 1);
        productRepo.save(product);

        //재입고 알림 전송 상태 기록
        ProductNotificationHistory history = new ProductNotificationHistory(productId, product.getRestockCount(), NotificationStatus.IN_PROGRESS);
        notificationHistoryRepo.save(history);

        //상품에 대해 알림을 설정한 유저 목록 조회
        List<ProductUserNotification> userNotificationList = userNotificationRepo.findByProductIdAndActiveTrue(productId);

        //상품이 재입고 되었을 때, 유저에게 알림 전송 시도
        for (ProductUserNotification userNotification : userNotificationList) {
            if (!product.isStock()) { //재고가 없는 경우
                System.out.println("재고가 부족하여 알림 전송을 중단합니다.");
                history.setStatus(NotificationStatus.CANCELED_BY_SOLD_OUT);
                notificationHistoryRepo.save(history);
                break;
            }else{ //재고가 있는 경우
                history.setStatus(NotificationStatus.COMPLETED);
                ProductUserNotificationHistory notificationHistory = new ProductUserNotificationHistory(product.getId(), userNotification.getUserId(), product.getRestockCount());
                userNotificationHistoryRepo.save(notificationHistory);
            }
        }
        notificationHistoryRepo.save(history);
        return new RestockNotificationResponse(history);
    }

}
