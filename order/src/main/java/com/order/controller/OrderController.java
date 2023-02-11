package com.order.controller;

import com.order.dto.OrderRequest;
import com.order.model.Order;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order created successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAllOrder(){
        List<Order> orders = orderService.findAllOrder();

        return orders;
    }
}
