package br.com.sergio.customer_management_api.service;

import br.com.sergio.customer_management_api.dto.CustomerRequestDTO;
import br.com.sergio.customer_management_api.dto.CustomerResponseDTO;
import br.com.sergio.customer_management_api.database.entity.Customer;
import br.com.sergio.customer_management_api.exception.CustomerNotFoundException;
import br.com.sergio.customer_management_api.database.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.LongToIntFunction;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        Customer customer = new Customer();

        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhoneNumber(dto.phoneNumber());

        Customer savedCustomer = customerRepository.save(customer);

        return toResponseDTO(savedCustomer);
    }

    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> findAll(Pageable pageable) {

        return customerRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO findById(Long id) {
        Customer customer = findCustomerById(id);

        return toResponseDTO(customer);
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO findByEmail(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));

        return toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer customer = findCustomerById(id);

        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhoneNumber(dto.phoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return toResponseDTO(updatedCustomer);
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = findCustomerById(id);

        customerRepository.delete(customer);
    }
}

