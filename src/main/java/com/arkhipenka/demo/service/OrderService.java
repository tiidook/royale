package com.arkhipenka.demo.service;

import com.arkhipenka.demo.entity.ItemOrder;
import com.arkhipenka.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public ItemOrder saveOrder(ItemOrder order){
        return orderRepository.save(order);
    }
}
