package com.webstore.controller;

import com.webstore.model.Order;
import com.webstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @PostMapping("/checkout")
//    public Order checkout(@RequestBody CheckoutRequest request) {
//        return orderService.createOrder(request);
//    }
}