package br.com.sergio.customer_management_api.controller;


import br.com.sergio.customer_management_api.dto.OrderRequestDTO;
import br.com.sergio.customer_management_api.dto.OrderResponseDTO;
import br.com.sergio.customer_management_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Endpoints for creating, listing, retrieving, and canceling orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for an existing customer using active products and calculates the total amount.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid request data or inactive product"),
            @ApiResponse(responseCode = "404", description = "Customer or product not found")})
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "List all orders",
            description = "Returns a paginated and sorted list of orders.")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<OrderResponseDTO> response = orderService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Find order by id",
            description = "Returns the details of a specific order.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        OrderResponseDTO response = orderService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels an existing order by changing its status from PENDING_PAYMENT to CANCELED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "409", description = "Order is already canceled")})
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id) {
        OrderResponseDTO response = orderService.cancel(id);
        return ResponseEntity.ok(response);
    }
}
