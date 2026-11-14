package com.hardy.sakafo.customer.service;

import com.hardy.sakafo.customer.dto.CustomerRequest;
import com.hardy.sakafo.customer.dto.CustomerResponse;
import com.hardy.sakafo.customer.exception.CustomerNotFoundException;
import com.hardy.sakafo.customer.mapper.CustomerMapper;
import com.hardy.sakafo.customer.model.Customer;
import com.hardy.sakafo.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = this.repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = this.repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format(
                                "Cannot update customer: No customer found with the provided ID: %s",
                                request.id()
                        )
                ));

        mergeCustomer(customer, request);
        this.repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (request.firstName()!= null && !request.firstName().isBlank()) {
            customer.setFirstName(request.firstName());
        }

        if (request.lastName() != null && !request.lastName().isBlank()) {
            customer.setLastName(request.lastName());
        }

        if (request.email() != null && !request.email().isBlank()) {
            customer.setEmail(request.email());
        }

        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromCustomer)
                .toList();
    }

    public CustomerResponse findById(String id) {
        return this.repository.findById(id)
                .map(this.mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("No customer found with the provided ID: %s", id)
                ));
    }

    public boolean existsById(String id) {
        return this.repository.existsById(id);
    }

    public void deleteCustomer(String id) {
        if (!this.repository.existsById(id)) {
            throw new CustomerNotFoundException(
                    String.format("No customer found with the provided ID: %s", id)
            );
        }

        this.repository.deleteById(id);
    }
}
