package br.com.sergio.customer_management_api.dto;

import br.com.sergio.customer_management_api.enums.PaymentProcessingResult;
import jakarta.validation.constraints.NotNull;

public record PaymentProcessingRequestDTO(

        @NotNull(message = "Payment processing result is required")
        PaymentProcessingResult result

) {
}
