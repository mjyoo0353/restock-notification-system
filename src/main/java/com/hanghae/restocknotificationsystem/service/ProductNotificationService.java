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
        //상품 조회 및 검증
        Product product = getProductOrThrowException(productId);

        //재입고 회차 증가
        product.setRestockCount(product.getRestockCount() + 1);
        productRepo.save(product);

        //재입고 알림 전송 상태 기록
        ProductNotificationHistory history = new ProductNotificationHistory(productId, product.getRestockCount(), NotificationStatus.IN_PROGRESS);
        notificationHistoryRepo.save(history);

        //상품에 대해 알림을 설정한 유저 목록 조회
        List<ProductUserNotification> notifiedUsers = userNotificationRepo.findByProductIdAndActiveTrue(productId);

        //상품이 재입고 되었을 때, 유저에게 알림 전송 시도
        for (ProductUserNotification user : notifiedUsers) {
            try{
                if (!product.isStock()) { //재고가 없는 경우
                    updateNotificationStatus(history,NotificationStatus.CANCELED_BY_SOLD_OUT);
                    break;
                }
                //알림 발송
                sendNotificationToUser(user, product, product.getRestockCount());
                //마지막으로 알림을 보낸 유저 기록
                history.setLastSentUserId(user.getUserId());
                updateNotificationStatus(history, NotificationStatus.COMPLETED);
            }catch (Exception e){
                updateNotificationStatus(history, NotificationStatus.CANCELED_BY_ERROR);
                throw e;
            }
        }
        return new RestockNotificationResponse(history);
    }

    private void updateNotificationStatus(ProductNotificationHistory history, NotificationStatus status) {
        history.setStatus(status);
        notificationHistoryRepo.save(history);
    }

    private Product getProductOrThrowException(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return product;
    }

    //알림 전송 (실제로는 메시지를 보내지 않음, 상태만 기록)
    private void sendNotificationToUser(ProductUserNotification userNotification, Product product, int restockCount) {
        // 재입고 알림 전송의 상태 테스트를 위한 재고 감소 코드
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            productRepo.save(product);
        }
        System.out.println("재고가 부족하여 알림 전송을 중단합니다.");

        //알림 전송 후 기록 저장
        ProductUserNotificationHistory notificationHistory = new ProductUserNotificationHistory(product.getId(), userNotification.getUserId(), restockCount);
        userNotificationHistoryRepo.save(notificationHistory);
    }

}
