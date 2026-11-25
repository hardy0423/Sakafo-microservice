package com.hardy.sakafo.order.orderline.mapper;

import com.hardy.sakafo.order.orderline.model.OrderLine;
import com.hardy.sakafo.order.product.dto.PurchaseRequest;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    public OrderLine toOrderLine(
            PurchaseRequest request,
            PurchaseResponse product
    ) {
        return OrderLine.builder()
                .productId(request.productId())
                .productName(product.name())
                .productDescription(product.description())
                .productPrice(product.price())
                .quantity(request.quantity())
                .build();
    }

    public PurchaseResponse toPurchaseResponse(OrderLine orderLine) {
        return new PurchaseResponse(
                orderLine.getProductId(),
                orderLine.getProductName(),
                orderLine.getProductDescription(),
                orderLine.getProductPrice(),
                orderLine.getQuantity()
        );
    }
}