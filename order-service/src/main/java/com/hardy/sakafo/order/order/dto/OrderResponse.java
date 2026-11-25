package com.hardy.sakafo.order.order.dto;

import com.hardy.sakafo.order.order.model.PaymentMethod;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Integer id,

        String reference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerId,

        List<PurchaseResponse> products,

        LocalDateTime createdDate,

        LocalDateTime lastModifiedDate

) {
}