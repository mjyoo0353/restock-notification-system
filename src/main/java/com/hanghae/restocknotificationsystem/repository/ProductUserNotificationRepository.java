package com.hanghae.restocknotificationsystem.repository;

import com.hanghae.restocknotificationsystem.entity.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {

}
