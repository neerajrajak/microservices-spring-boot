package com.inventory.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.example.dto.InventoryResponse;
import com.inventory.example.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	@SneakyThrows        				// do not use this in production
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		log.info("Wait Started.");
		Thread.sleep(10000);
		log.info("Wait Started.");
		return inventoryRepository.findBySkuCodeIn(skuCode)
				.stream()
				.map(inventory -> InventoryResponse.builder()
										.skuCode(inventory.getSkuCode())
										.isInStock(inventory.getQuantity() > 0)
										.build()
					).toList();
	}
}
