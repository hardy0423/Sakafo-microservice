package com.hardy.sakafo.customer.dto;

import com.hardy.sakafo.customer.model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
