package com.inventory.controller;

import com.inventory.dto.InventoryResponse;
import com.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // for Path variable localhost:8080/api/inventory/iphone12,iphone13,iphone14,iphone15
    // for query parameter localhost:8080/api/inventory?sku="iphone12&sku="iphone13&sku="iphone14&sku="iphone15
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> sku){
        List<InventoryResponse> inventoryResponses = inventoryService.isInStock(sku);

        System.out.println("inventoryResponses="+inventoryResponses.size());

        return inventoryResponses;
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> allStock(){
        List<InventoryResponse> inventoryResponses = inventoryService.findAllStocks();

        return inventoryResponses;
    }



}
