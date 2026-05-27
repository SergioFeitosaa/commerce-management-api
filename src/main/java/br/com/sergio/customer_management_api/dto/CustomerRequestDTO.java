package br.com.sergio.customer_management_api.dto;

public record CustomerRequestDTO(
        String name,
        String email,
        String cpf,
        String phoneNumber) {
}
