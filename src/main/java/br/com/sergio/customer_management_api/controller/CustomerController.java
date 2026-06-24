package br.com.sergio.customer_management_api.controller;

import br.com.sergio.customer_management_api.dto.CustomerRequestDTO;
import br.com.sergio.customer_management_api.dto.CustomerResponseDTO;
import br.com.sergio.customer_management_api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Operations related to customer management")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create a new customer", description = "Create a new customer with name and email")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid customer data")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List all customers", description = "Retrieve a paginated list of registered customers")
    @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(@PageableDefault(
            page = 0,
            size = 10,
            sort = "id",
            direction = Sort.Direction.ASC
    ) Pageable pageable) {
        Page<CustomerResponseDTO> response = customerService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get customer by ID", description = "Retrieve a customer by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        CustomerResponseDTO response = customerService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Operation(summary = "Get customer by Email", description = "Retrieve a customer by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> findByEmail(@PathVariable String email) {
        CustomerResponseDTO response = customerService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update customer", description = "Update an existing customer by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Customer update successfully")
    @ApiResponse(responseCode = "400", description = "Invalid customer data")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete customer", description = "Delete an existing customer by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Customer delete successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
