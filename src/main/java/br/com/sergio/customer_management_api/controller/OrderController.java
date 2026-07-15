package br.com.sergio.customer_management_api.controller;


import br.com.sergio.customer_management_api.dto.OrderRequestDTO;
import br.com.sergio.customer_management_api.dto.OrderResponseDTO;
import br.com.sergio.customer_management_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity <OrderResponseDTO> create (@Valid @RequestBody OrderRequestDTO dto){
        OrderResponseDTO response = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
