package br.com.sergio.customer_management_api.service;

import br.com.sergio.customer_management_api.dto.CustomerRequestDTO;
import br.com.sergio.customer_management_api.dto.CustomerResponseDTO;
import br.com.sergio.customer_management_api.entity.Customer;
import br.com.sergio.customer_management_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        Customer customer = new Customer();

        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhoneNumber(dto.phoneNumber());

        Customer customesaved = customerRepository.save(customer);
        return new CustomerResponseDTO(
                customesaved.getId(),
                customesaved.getName(),
                customesaved.getEmail()
        );
    }

    public List<CustomerResponseDTO> findAll() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail()))
                .toList();
    }

    public CustomerResponseDTO findById(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow();
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );

    }


}

