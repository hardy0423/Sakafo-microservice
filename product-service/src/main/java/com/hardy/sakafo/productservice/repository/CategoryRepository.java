
package com.hardy.sakafo.productservice.repository;

import com.hardy.sakafo.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
