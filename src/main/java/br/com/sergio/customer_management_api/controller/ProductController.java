package br.com.sergio.customer_management_api.controller;

import br.com.sergio.customer_management_api.dto.ProductRequestDTO;
import br.com.sergio.customer_management_api.dto.ProductResponseDTO;
import br.com.sergio.customer_management_api.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(
        name = "Products",
        description = "Endpoints for creating, listing, retrieving, updating, activating, and deactivating products")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with validated product data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")})
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "List all products",
            description = "Returns a paginated and sorted list of registered products.")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        Page<ProductResponseDTO> response = productService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Find product by ID",
            description = "Returns the details of a product using its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product using its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = productService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deactivate a product",
            description = "Deactivates an existing product without deleting it from the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ProductResponseDTO> deactivate(@PathVariable Long id) {
        ProductResponseDTO response = productService.deactivate(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Activate a product",
            description = "Activates an existing product using its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product activated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductResponseDTO> activate(@PathVariable Long id) {
        ProductResponseDTO response = productService.activate(id);
        return ResponseEntity.ok(response);
    }
}