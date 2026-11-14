package com.hardy.sakafo.customer.dto;

import com.hardy.sakafo.customer.model.Address;

public record CustomerRequest(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {}
