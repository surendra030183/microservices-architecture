package com.inventory;

import com.inventory.model.Inventory;
import com.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryApplication {

	public static void main(String[] args) {

		//System.out.println("hi");
		SpringApplication.run(InventoryApplication.class, args);

	}

	//@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSku("iphone_12");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSku("iphone_13");
			inventory1.setQuantity(50);

			Inventory inventory2 = new Inventory();
			inventory2.setSku("iphone_14");
			inventory2.setQuantity(100);

			Inventory inventory3 = new Inventory();
			inventory3.setSku("iphone_15");
			inventory3.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
		};
	}

}
