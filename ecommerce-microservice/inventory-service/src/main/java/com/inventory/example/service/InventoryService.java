package com.inventory.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.example.dto.InventoryResponse;
import com.inventory.example.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepository.findBySkuCodeIn(skuCode)
				.stream()
				.map(inventory -> InventoryResponse.builder()
										.skuCode(inventory.getSkuCode())
										.isInStock(inventory.getQuantity() > 0)
										.build()
					).toList();
	}
}
