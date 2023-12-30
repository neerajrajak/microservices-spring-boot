package com.ordermicros.example.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ordermicros.example.dto.OrderLineItemDto;
import com.ordermicros.example.dto.OrderRequest;
import com.ordermicros.example.model.Order;
import com.ordermicros.example.model.OrderLineItem;
import com.ordermicros.example.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
	
	private final OrderRepository orderRepository;

	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItems);
        
        orderRepository.save(order);
        log.info("Order created with Order Id {}",order.getOrderNumber());
        return order.getOrderNumber();
	}
	
	private OrderLineItem mapToDto(OrderLineItemDto orderLineItemsDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemsDto.getPrice());
        orderLineItem.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItem;
    }
}
