package br.com.sergio.customer_management_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequestDTO(

        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID must be greater than zero")
        Long orderId

) {
}
