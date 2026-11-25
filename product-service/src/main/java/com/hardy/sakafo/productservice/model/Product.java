
package com.hardy.sakafo.productservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "available_quantity")
    private double availableQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
