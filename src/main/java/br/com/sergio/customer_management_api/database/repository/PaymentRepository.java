package br.com.sergio.customer_management_api.database.repository;

import br.com.sergio.customer_management_api.database.entity.Payment;
import br.com.sergio.customer_management_api.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByOrderIdAndStatus(Long id, PaymentStatus status);

}