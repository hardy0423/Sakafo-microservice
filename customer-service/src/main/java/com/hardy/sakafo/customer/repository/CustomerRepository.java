package com.hardy.sakafo.customer.repository;

import com.hardy.sakafo.customer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
