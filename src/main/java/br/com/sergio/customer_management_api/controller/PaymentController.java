package br.com.sergio.customer_management_api.controller;

import br.com.sergio.customer_management_api.dto.PaymentProcessingRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentRequestDTO;
import br.com.sergio.customer_management_api.dto.PaymentResponseDTO;
import br.com.sergio.customer_management_api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints for creating and processing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Create a new payment",
            description = "Creates a new payment attempt for an existing order and initializes the payment with PENDING status.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "409", description = "Payment already exists or pending payment already exists")})
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentRequestDTO dto) {

        PaymentResponseDTO response = paymentService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Process a payment",
            description = "Processes a pending payment by updating its status according to the processing result.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "409", description = "Payment has already been processed")})
    @PatchMapping("/{id}/processPayment")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @PathVariable Long id, @Valid @RequestBody PaymentProcessingRequestDTO dto) {

        PaymentResponseDTO response = paymentService.processPayment(id, dto);

        return ResponseEntity.ok(response);

    }

}
