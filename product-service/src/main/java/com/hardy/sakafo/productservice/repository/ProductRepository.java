
package com.hardy.sakafo.productservice.repository;

import com.hardy.sakafo.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
