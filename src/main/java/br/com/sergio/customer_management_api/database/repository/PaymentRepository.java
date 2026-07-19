package br.com.sergio.customer_management_api.database.repository;

import br.com.sergio.customer_management_api.database.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
