package com.inventory.service;

import com.inventory.dto.InventoryResponse;
import com.inventory.model.Inventory;
import com.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuList){
        log.info("Checking ordered items in inventory");

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skuList);

        return inventoryList.stream()
                .map(inventory -> InventoryResponse.builder()
                        .sku(inventory.getSku())
                        .isInStock(inventory.getQuantity()>0)
                        .build())
                .toList();

    }

    public List<InventoryResponse> findAllStocks() {

        log.info("Checking all items in inventory");
        List<Inventory> inventoryList = inventoryRepository.findAll();


        log.info("Inventory item {} ", inventoryList.size());

        return inventoryList.stream()
                .map(inventory -> InventoryResponse.builder()
                        .sku(inventory.getSku())
                        .isInStock( inventory.getQuantity()>0)
                        .build())
                .toList();
    }
}
