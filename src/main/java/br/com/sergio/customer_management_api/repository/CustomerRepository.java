package br.com.sergio.customer_management_api.repository;

import br.com.sergio.customer_management_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer, Long> {

}
