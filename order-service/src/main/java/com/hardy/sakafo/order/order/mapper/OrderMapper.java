package com.hardy.sakafo.order.order.mapper;

import com.hardy.sakafo.order.order.dto.OrderRequest;
import com.hardy.sakafo.order.order.dto.OrderResponse;
import com.hardy.sakafo.order.order.model.Order;
import com.hardy.sakafo.order.orderline.mapper.OrderLineMapper;
import com.hardy.sakafo.order.orderline.model.OrderLine;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderLineMapper orderLineMapper;

    public Order toOrder(
            OrderRequest request,
            List<PurchaseResponse> products
    ) {
        Order order = Order.builder()
                .id(request.id())
                .reference(request.reference())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();

        Map<Integer, PurchaseResponse> productsById = products.stream()
                .collect(Collectors.toMap(
                        PurchaseResponse::productId,
                        Function.identity(),
                        (first, second) -> first
                ));

        List<OrderLine> orderLines = request.products().stream()
                .map(productRequest -> orderLineMapper.toOrderLine(
                        productRequest,
                        productsById.get(productRequest.productId())
                ))
                .peek(orderLine -> orderLine.setOrder(order))
                .collect(Collectors.toCollection(ArrayList::new));

        order.setOrderLines(orderLines);

        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId(),
                order.getOrderLines().stream()
                        .map(orderLineMapper::toPurchaseResponse)
                        .toList(),
                order.getCreatedDate(),
                order.getLastModifiedDate()
        );
    }
}