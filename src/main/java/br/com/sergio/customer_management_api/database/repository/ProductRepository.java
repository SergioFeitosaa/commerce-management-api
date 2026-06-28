package br.com.sergio.customer_management_api.database.repository;

import br.com.sergio.customer_management_api.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
