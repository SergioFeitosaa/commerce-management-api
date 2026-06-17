package br.com.sergio.customer_management_api.service;

import br.com.sergio.customer_management_api.dto.CustomerRequestDTO;
import br.com.sergio.customer_management_api.dto.CustomerResponseDTO;
import br.com.sergio.customer_management_api.entity.Customer;
import br.com.sergio.customer_management_api.exception.CustomerNotFoundException;
import br.com.sergio.customer_management_api.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should create customer successfully")
    public void shouldCreateCustomerSuccessfully() {

        //ARRANGE
        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Sérgio",
                "sergio@gmail.com",
                "12345678901",
                "81999999999"
        );

        Customer customerSaved = new Customer();
        customerSaved.setId(1L);
        customerSaved.setName("Sérgio");
        customerSaved.setEmail("sergio@email.com");
        customerSaved.setCpf("12345678901");

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customerSaved);

        //ACT
        CustomerResponseDTO response = customerService.create(dto);

        //ASSERT
        assertEquals(1L, response.id());
        assertEquals("Sérgio", response.name());
        assertEquals("sergio@email.com", response.email());

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should return all customers")
    public void shouldReturnAllCustomers() {

        //ARRANGE
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Sérgio");
        customer1.setEmail("sergio@email.com");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Maria");
        customer2.setEmail("maria@email.com");

        when(customerRepository.findAll())
                .thenReturn(List.of(customer1, customer2));

        //ACT
        List<CustomerResponseDTO> response = customerService.findAll();

        //ASSERT
        assertEquals(2, response.size());

        assertEquals(1L, response.get(0).id());
        assertEquals("Sérgio", response.get(0).name());
        assertEquals("sergio@email.com", response.get(0).email());

        assertEquals(2L, response.get(1).id());
        assertEquals("Maria", response.get(1).name());
        assertEquals("maria@email.com", response.get(1).email());
    }

    @Test
    @DisplayName("Should return empty list when no customers exist")
    public void shouldReturnEmptyListWhenNoCustomersExist() {

        // ARRANGE
        when(customerRepository.findAll())
                .thenReturn(List.of());

        // ACT
        List<CustomerResponseDTO> response =
                customerService.findAll();

        // ASSERT
        assertTrue(response.isEmpty());
        assertEquals(0, response.size());
    }

    @Test
    @DisplayName("Should return customer when ID exists")
    public void shouldReturnCustomerWhenIdExists() {

        //ARRANGE
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Sérgio");
        customer.setEmail("sergio@email.com");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        //ACT
        CustomerResponseDTO response = customerService.findById(1L);

        //ASSERT
        assertEquals(1L, response.id());
        assertEquals("Sérgio", response.name());
        assertEquals("sergio@email.com", response.email());
    }

    @Test
    @DisplayName("Should throw customer not found exception when ID does not exist")
    public void shouldThrowCustomerNotFoundExceptionWhenFindByIdDoesNotExist() {

        //ARRANGE
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.findById(1L));

        //ACT e ASSERT
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Should update customer when ID exists")
    public void shouldUpdateCustomerWhenIdExists() {

        // ARRANGE
        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Sérgio",
                "sergio@gmail.com",
                "12345678901",
                "81999999999"
        );

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Nome Antigo");
        customer.setEmail("email.antigo@gmail.com");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        // ACT
        CustomerResponseDTO response =
                customerService.update(1L, dto);

        // ASSERT
        assertEquals(1L, response.id());
        assertEquals("Sérgio", response.name());
        assertEquals("sergio@gmail.com", response.email());

        verify(customerRepository).findById(1L);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw customer not found exception when updating non existing customer")
    public void shouldThrowCustomerNotFoundExceptionWhenUpdatingNonExistingCustomer() {

        // ARRANGE
        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Sérgio",
                "sergio@gmail.com",
                "12345678901",
                "81999999999"
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        // ACT
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.update(1L, dto)
        );

        // ASSERT
        assertEquals(
                "Customer not found with id: 1",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Should delete customer when ID exists")
    public void shouldDeleteCustomerWhenIdExist() {
        //ARRANGE
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Sérgio");
        customer.setEmail("sergio@email.com");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        //ACT
        customerService.delete(1L);

        //ASSERT
        verify(customerRepository).delete(customer);
    }

    @Test
    @DisplayName("Should delete customer when ID doesnt not exists")
    public void shouldThrowCustomerNotFoundExceptionWhenDeleteIdDoesNotExist() {
        //ARRANGE
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        //ACT
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.delete(1L)
        );

        //ASSERT
        assertEquals("Customer not found with id: 1", exception.getMessage());

    }
}