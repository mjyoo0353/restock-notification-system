package com.hanghae.restocknotificationsystem.repository;

import com.hanghae.restocknotificationsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
