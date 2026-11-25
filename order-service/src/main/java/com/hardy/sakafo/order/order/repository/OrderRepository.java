
package com.hardy.sakafo.order.order.repository;

import com.hardy.sakafo.order.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
