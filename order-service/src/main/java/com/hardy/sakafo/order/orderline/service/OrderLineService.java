package com.hardy.sakafo.order.orderline.service;

import com.hardy.sakafo.order.orderline.mapper.OrderLineMapper;
import com.hardy.sakafo.order.orderline.repository.OrderLineRepository;
import com.hardy.sakafo.order.product.dto.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public List<PurchaseResponse> findAllByOrderId(Integer orderId) {

        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toPurchaseResponse)
                .toList();
    }
}