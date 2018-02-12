package com.arkhipenka.demo.controller;

import com.arkhipenka.demo.entity.Item;
import com.arkhipenka.demo.entity.ItemOrder;
import com.arkhipenka.demo.service.ItemService;
import com.arkhipenka.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    private ResponseEntity<List<Item>> getAllItems(){
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    private ResponseEntity<ItemOrder> postOrder(@RequestBody ItemOrder order){
        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.OK);
    }
}
