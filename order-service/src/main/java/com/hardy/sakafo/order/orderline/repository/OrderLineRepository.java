
package com.hardy.sakafo.order.orderline.repository;

import com.hardy.sakafo.order.orderline.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
