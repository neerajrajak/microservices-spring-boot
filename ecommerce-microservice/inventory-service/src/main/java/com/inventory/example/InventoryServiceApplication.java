package com.inventory.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.inventory.example.model.Inventory;
import com.inventory.example.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//		return args->{
//			Inventory invetory = new Inventory();
//			invetory.setSkuCode("iphone_1001");
//			invetory.setQuantity(100);
//			
//			Inventory invetory2 = new Inventory();
//			invetory2.setSkuCode("iphone_1002");
//			invetory2.setQuantity(0);
//			
//			inventoryRepository.save(invetory);
//			inventoryRepository.save(invetory2);
//		};
//	}

}
