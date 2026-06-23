package br.com.sergio.customer_management_api.database.repository;

import br.com.sergio.customer_management_api.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
