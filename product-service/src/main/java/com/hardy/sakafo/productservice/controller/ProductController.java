
package com.hardy.sakafo.productservice.controller;

import com.hardy.sakafo.productservice.dto.PurchaseRequest;
import com.hardy.sakafo.productservice.dto.PurchaseResponse;
import com.hardy.sakafo.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseResponse>> purchaseProducts(
            @RequestBody List<@Valid PurchaseRequest> request
    ) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }
}
