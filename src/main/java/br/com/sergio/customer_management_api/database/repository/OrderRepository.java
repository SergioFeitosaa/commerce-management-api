package br.com.sergio.customer_management_api.database.repository;

import br.com.sergio.customer_management_api.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
