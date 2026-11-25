package com.hardy.sakafo.order.product.dto;
import java.math.BigDecimal;

public record PurchaseResponse(

        Integer productId,

        String name,

        String description,

        BigDecimal price,

        double quantity

) {
}
