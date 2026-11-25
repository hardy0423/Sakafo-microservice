
package com.hardy.sakafo.productservice.mapper;

import com.hardy.sakafo.productservice.dto.PurchaseResponse;
import com.hardy.sakafo.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public PurchaseResponse toPurchaseResponse(
            Product product,
            double quantity
    ) {
        return new PurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
