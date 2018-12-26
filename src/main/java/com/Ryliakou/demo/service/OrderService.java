package com.Ryliakou.demo.service;

import com.Ryliakou.demo.entity.ClientOrder;
import com.Ryliakou.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public ClientOrder saveOrder(ClientOrder order){
        return orderRepository.save(order);
    }
    public List<ClientOrder> getAllItemOrders(){
        return orderRepository.findAll();
    }
}
