package com.hardy.sakafo.order.customer.client;
import com.hardy.sakafo.order.customer.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("/api/v1/customers/{customer-id}")
    CustomerResponse findCustomerById( @PathVariable("customer-id") String customerId
    );
}
