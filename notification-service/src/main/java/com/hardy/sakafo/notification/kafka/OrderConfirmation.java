package com.hardy.sakafo.notification.kafka;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Integer orderId,
        String orderReference,
        BigDecimal totalAmount,
        String paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
    public record CustomerResponse(
            String id,
            String firstname,
            String lastname,
            String email,
            Address address
    ) {
    }

    public record Address(
            String street,
            String houseNumber,
            String zipCode
    ) {
    }

    public record PurchaseResponse(
            Integer productId,
            String name,
            String description,
            BigDecimal price,
            double quantity
    ) {
    }
}
