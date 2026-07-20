package br.com.sergio.customer_management_api.dto;

import br.com.sergio.customer_management_api.enums.PaymentMethod;
import br.com.sergio.customer_management_api.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(

        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime createdAt,
        LocalDateTime processedAt

) {
}
