package com.ordermicros.example.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ordermicros.example.dto.InventoryResponse;
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

	private final WebClient.Builder webClientBuilder;

	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLineItems(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();


		// call inventory service and check if sku is in stock and den place order
		   InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
	                .uri("http://inventory-service/api/inventory",
	                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
	                .retrieve()
	                .bodyToMono(InventoryResponse[].class)
	                .block();
		   
		   boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
	                .allMatch(InventoryResponse::isInStock);
		
		if(allProductsInStock) {
			orderRepository.save(order);
			log.info("Order created with Order Id {}", order.getOrderNumber());
			return order.getOrderNumber();
		} else {
			throw new IllegalArgumentException("Product is not in stock, Please try again later.");
		}
		
	}

	private OrderLineItem mapToDto(OrderLineItemDto orderLineItemsDto) {
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setPrice(orderLineItemsDto.getPrice());
		orderLineItem.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItem.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItem;
	}
}
