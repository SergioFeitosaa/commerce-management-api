package br.com.sergio.customer_management_api.controller;

import br.com.sergio.customer_management_api.dto.PaymentProcessingRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentResponseDTO;
import br.com.sergio.customer_management_api.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentRequestDTO dto) {

        PaymentResponseDTO response = paymentService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/processPayment")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @PathVariable Long id, @Valid @RequestBody PaymentProcessingRequestDTO dto) {

        PaymentResponseDTO response = paymentService.processPayment(id, dto);

        return ResponseEntity.ok(response);

    }

}
