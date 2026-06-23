package br.com.sergio.customer_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "CPF é Obrigatório")
        @Pattern(regexp = "\\d{11}",
                message = "CPF deve conter 11 números"
        )
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "\\d{10,11}",
                message = "Telefone deve conter 10/11 números")
        String phoneNumber) {
}
