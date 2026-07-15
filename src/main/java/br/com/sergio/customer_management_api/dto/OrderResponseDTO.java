package br.com.sergio.customer_management_api.dto;

import br.com.sergio.customer_management_api.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        String customerName,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items

) {
}
