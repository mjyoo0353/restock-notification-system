package com.hanghae.restocknotificationsystem.repository;

import com.hanghae.restocknotificationsystem.entity.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
