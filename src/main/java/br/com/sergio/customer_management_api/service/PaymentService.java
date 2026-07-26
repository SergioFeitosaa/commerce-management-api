package br.com.sergio.customer_management_api.service;

import br.com.sergio.customer_management_api.database.entity.Order;
import br.com.sergio.customer_management_api.database.entity.Payment;
import br.com.sergio.customer_management_api.database.repository.OrderRepository;
import br.com.sergio.customer_management_api.database.repository.PaymentRepository;
import br.com.sergio.customer_management_api.dto.PaymentProcessingRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentResponseDTO;
import br.com.sergio.customer_management_api.enums.PaymentMethod;
import br.com.sergio.customer_management_api.enums.PaymentStatus;
import br.com.sergio.customer_management_api.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {

        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id:" + dto.orderId()));

        if (paymentRepository.existsByOrderIdAndStatus(dto.orderId(), PaymentStatus.APPROVED)) {
            throw new OrderAlreadyPaidException("Order already paid with id: " + dto.orderId());

        } else if (paymentRepository.existsByOrderIdAndStatus(dto.orderId(), PaymentStatus.PENDING)) {
            throw new PendingPaymentAlreadyExistsException("Pending payment already exists with order id: " + dto.orderId());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(PaymentMethod.PIX);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setProcessedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return toPaymentResponseDTO(savedPayment);
    }

    private PaymentResponseDTO toPaymentResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getProcessedAt()
        );
    }

    private Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
    }

    @Transactional
    public PaymentResponseDTO processPayment(Long paymentId, PaymentProcessingRequestDTO dto) {
        Payment payment = findPaymentById(paymentId);

        if (!payment.getStatus().equals(PaymentStatus.PENDING)) {
            throw new PaymentAlreadyProcessedException("Payment already exists with id: " + paymentId);
        }

        switch (dto.result()) {

            case APPROVED -> payment.setStatus(PaymentStatus.APPROVED);

            case REJECTED -> payment.setStatus(PaymentStatus.REJECTED);

        }

        payment.setProcessedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return toPaymentResponseDTO(savedPayment);

    }
}
