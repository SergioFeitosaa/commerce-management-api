package br.com.sergio.customer_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(

        @NotBlank(message = "Customer name is required")
        @Size(min = 3, max = 100, message = "Customer name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Customer email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Customer CPF is required")
        @Pattern(
                regexp = "\\d{11}",
                message = "CPF must contain 11 digits"
        )
        String cpf,

        @NotBlank(message = "Customer phone number is required")
        @Pattern(
                regexp = "\\d{10,11}",
                message = "Phone number must contain 10 or 11 digits"
        )
        String phoneNumber) {
}
