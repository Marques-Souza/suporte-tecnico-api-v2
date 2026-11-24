package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.CreateOrderRequest;
import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateOrderStatusRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateOrderUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.FindOrderByIdUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.ListOrdersUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.UpdateOrderStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "gerenciamento de ordens")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, FindOrderByIdUseCase findOrderByIdUseCase, ListOrdersUseCase listOrdersUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @PostMapping
    @Operation(summary = "Cadastra uma ordem")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request){
        OrderResponse response = createOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma ordem por ID")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findOrderByIdUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Lista as ordens")
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(listOrdersUseCase.execute());
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de uma ordem")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateOrderStatusRequest request ){
        return ResponseEntity.ok(updateOrderStatusUseCase.execute(id, request));
    }
}
