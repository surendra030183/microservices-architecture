package com.order.service;

import com.order.dto.InventoryResponse;
import com.order.dto.OrderItemsDto;
import com.order.dto.OrderRequest;
import com.order.model.Order;
import com.order.model.OrderItems;
import com.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();

        // generate unique order number and set to order
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = orderRequest.getOrderItemsDtoList()
                .stream()
                .map(this::mapDtoToEntity)
                .toList();

        // set order items to order
        order.setOrderItems(orderItems);

        // order sku list and check sku in inventory service for availability
        List<String> skuListOrdered = orderItems.stream()
                .map(OrderItems::getSku)
                .toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("sku", skuListOrdered).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allSkuInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allSkuInStock) {
            // save order
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Selected product not in stock, try again later");
        }
    }

    private <R> OrderItems mapDtoToEntity(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setSku(orderItemsDto.getSku());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setPrice(orderItemsDto.getPrice());

        return orderItems;
    }

    public List<Order> findAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
}
