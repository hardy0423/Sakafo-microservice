
package com.hardy.sakafo.productservice.service;

import com.hardy.sakafo.productservice.dto.PurchaseRequest;
import com.hardy.sakafo.productservice.dto.PurchaseResponse;
import com.hardy.sakafo.productservice.mapper.ProductMapper;
import com.hardy.sakafo.productservice.model.Product;
import com.hardy.sakafo.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<PurchaseResponse> purchaseProducts(
            List<PurchaseRequest> request
    ) {

        // 1. Récupérer les identifiants des produits demandés
        List<Integer> productIds = request.stream()
                .map(PurchaseRequest::productId)
                .distinct()
                .toList();

        // 2. Récupérer les produits depuis la base de données
        Map<Integer, Product> products = repository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()
                ));

        // 3. Vérifier que tous les produits existent
        for (PurchaseRequest purchase : request) {

            Product product = products.get(purchase.productId());

            if (product == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with ID " + purchase.productId() + " not found"
                );
            }
        }

        // 4. Additionner les quantités demandées par produit
        Map<Integer, Double> requestedQuantities = request.stream()
                .collect(Collectors.groupingBy(
                        PurchaseRequest::productId,
                        Collectors.summingDouble(PurchaseRequest::quantity)
                ));

        // 5. Vérifier la disponibilité du stock
        requestedQuantities.forEach((productId, quantity) -> {

            Product product = products.get(productId);

            if (product.getAvailableQuantity() < quantity) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Insufficient stock for product: " + product.getName()
                );
            }
        });

        // 6. Construire les réponses
        return request.stream()
                .map(purchase -> mapper.toPurchaseResponse(
                        products.get(purchase.productId()),
                        purchase.quantity()
                ))
                .toList();
    }
}
