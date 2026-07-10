package br.com.sergio.customer_management_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequestDTO(

        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be greater than zero")
        Long customerId,

        @Valid
        @NotEmpty(message = "Order items are required")
        List<OrderItemRequestDTO> items

) {
}
