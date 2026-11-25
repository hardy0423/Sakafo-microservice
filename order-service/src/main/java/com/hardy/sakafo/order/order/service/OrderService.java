package com.hardy.sakafo.order.order.service;

import com.hardy.sakafo.order.customer.client.CustomerClient;
import com.hardy.sakafo.order.customer.dto.CustomerResponse;
import com.hardy.sakafo.order.kafka.OrderConfirmation;
import com.hardy.sakafo.order.order.dto.OrderRequest;
import com.hardy.sakafo.order.order.dto.OrderResponse;
import com.hardy.sakafo.order.order.mapper.OrderMapper;
import com.hardy.sakafo.order.order.model.Order;
import com.hardy.sakafo.order.order.repository.OrderRepository;
import com.hardy.sakafo.order.product.client.ProductClient;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    public Integer createOrder(OrderRequest request) {

        // 1. Check customer exists
        CustomerResponse customer = customerClient.findCustomerById(
                request.customerId()
        );

        // 2. Get product information
        List<PurchaseResponse> products = productClient.purchaseProducts(
                request.products()
        );

        // 3. Calculate total amount
        BigDecimal totalAmount = products.stream()
                .map(product -> product.price()
                        .multiply(BigDecimal.valueOf(product.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Build order
        Order order = mapper.toOrder(request, products);
        order.setTotalAmount(totalAmount);

        // 5. Save order
        Order savedOrder = repository.save(order);

        // 6. Publish order confirmation
        OrderConfirmation confirmation = new OrderConfirmation(
                savedOrder.getId(),
                savedOrder.getReference(),
                savedOrder.getTotalAmount(),
                savedOrder.getPaymentMethod(),
                customer,
                products
        );

        kafkaTemplate.send(
                "order-topic",
                savedOrder.getReference(),
                confirmation
        );

        return savedOrder.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .toList();
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No order found with the provided ID: " + id
                ));
    }
}