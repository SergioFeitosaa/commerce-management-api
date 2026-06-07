package br.com.sergio.customer_management_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponseDTO(
        int status,
        String message,
        List<String> errors,
        LocalDateTime timestamp
) {
}
