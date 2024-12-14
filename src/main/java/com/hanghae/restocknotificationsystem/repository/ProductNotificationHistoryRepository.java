package com.hanghae.restocknotificationsystem.repository;

import com.hanghae.restocknotificationsystem.entity.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
}
