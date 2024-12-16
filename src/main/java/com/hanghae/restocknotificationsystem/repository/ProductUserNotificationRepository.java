package com.hanghae.restocknotificationsystem.repository;

import com.hanghae.restocknotificationsystem.entity.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {

    //상품별 재입고 알림을 설정한 유저 정보 조회
    @Query("select pun from ProductUserNotification pun where pun.productId = :productId and pun.isActive = true")
    List<ProductUserNotification> findByProductIdAndActiveTrue(Long productId);
}
