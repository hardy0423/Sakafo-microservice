package com.hardy.sakafo.order.kafka;

import com.hardy.sakafo.order.customer.dto.CustomerResponse;
import com.hardy.sakafo.order.order.model.PaymentMethod;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Integer orderId,
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}