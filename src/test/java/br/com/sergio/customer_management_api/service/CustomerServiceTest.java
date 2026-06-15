package br.com.sergio.customer_management_api.service;

import br.com.sergio.customer_management_api.dto.CustomerResponseDTO;
import br.com.sergio.customer_management_api.entity.Customer;
import br.com.sergio.customer_management_api.exception.CustomerNotFoundException;
import br.com.sergio.customer_management_api.repository.CustomerRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

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