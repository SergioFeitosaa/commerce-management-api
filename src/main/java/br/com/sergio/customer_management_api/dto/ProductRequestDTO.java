package br.com.sergio.customer_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Product name is required")
        @Size (min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        String name,

        @Size(max = 500, message = "Product description must have at most 500 characters")
        String description,

        @NotNull(message = "Product price is required")
        @Positive (message = "Product price must be greater than zero")
        BigDecimal price
) {
}
