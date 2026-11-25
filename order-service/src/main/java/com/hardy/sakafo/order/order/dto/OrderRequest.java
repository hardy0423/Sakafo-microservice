package com.hardy.sakafo.order.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardy.sakafo.order.order.model.PaymentMethod;
import com.hardy.sakafo.order.product.dto.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public record OrderRequest(

        Integer id,

        String reference,

        @NotNull(message = "Payment method should be specified")
        PaymentMethod paymentMethod,

        @NotBlank(message = "Customer should be present")
        String customerId,

        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products

) {}