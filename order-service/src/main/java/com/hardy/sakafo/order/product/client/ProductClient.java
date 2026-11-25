package com.hardy.sakafo.order.product.client;

import com.hardy.sakafo.order.product.dto.PurchaseRequest;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/api/v1/products/purchase")
    List<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> products
    );
}