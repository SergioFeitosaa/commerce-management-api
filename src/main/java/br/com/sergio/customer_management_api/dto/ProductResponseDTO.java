package br.com.sergio.customer_management_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal price,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
