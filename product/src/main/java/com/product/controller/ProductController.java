package com.product.controller;

import com.product.dto.ProductRequest;
import com.product.dto.ProductResponse;
import com.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest request){
        productService.createProduct(request);
        return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        List<ProductResponse> products = productService.findAllProduct();
        return ResponseEntity.ok(products);
    }

}
