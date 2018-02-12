package com.arkhipenka.demo.repository;

import com.arkhipenka.demo.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ItemOrder, Long> {
}
