package br.com.sergio.customer_management_api.service;


import br.com.sergio.customer_management_api.database.entity.Customer;
import br.com.sergio.customer_management_api.database.entity.Order;
import br.com.sergio.customer_management_api.database.entity.OrderItem;
import br.com.sergio.customer_management_api.database.entity.Product;
import br.com.sergio.customer_management_api.database.repository.CustomerRepository;
import br.com.sergio.customer_management_api.database.repository.OrderRepository;
import br.com.sergio.customer_management_api.database.repository.ProductRepository;
import br.com.sergio.customer_management_api.dto.OrderItemRequestDTO;
import br.com.sergio.customer_management_api.dto.OrderItemResponseDTO;
import br.com.sergio.customer_management_api.dto.OrderRequestDTO;
import br.com.sergio.customer_management_api.dto.OrderResponseDTO;
import br.com.sergio.customer_management_api.enums.OrderStatus;
import br.com.sergio.customer_management_api.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + dto.customerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO item : dto.items()) {
            Long productId = item.productId();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

            if (!product.isActive()) {
                throw new ProductInactiveException("Product is inactive with id: " + productId);
            }

            Integer quantity = item.quantity();

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

            OrderItem orderItem = new OrderItem();

            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(subtotal);
            orderItem.setProduct(product);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return toOrderResponseDTO(savedOrder);
    }

    private OrderResponseDTO toOrderResponseDTO(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemResponseDTO> itemResponses = orderItems
                .stream()
                .map(orderItem -> new OrderItemResponseDTO(
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getUnitPrice(),
                        orderItem.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                itemResponses
        );

    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> findAll(Pageable page) {
        return orderRepository.findAll(page)
                .map(this::toOrderResponseDTO);
    }


    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {

        Order order = findOrderById(id);

        return toOrderResponseDTO(order);

    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public OrderResponseDTO cancel(Long id) {

        Order order = findOrderById(id);

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderAlreadyCanceledException("Order is already canceled with id: " + id);
        }

        order.setStatus(OrderStatus.CANCELED);

        return toOrderResponseDTO(order);
    }
}
