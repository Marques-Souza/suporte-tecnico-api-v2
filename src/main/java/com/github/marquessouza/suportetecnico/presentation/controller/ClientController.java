package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.CreateClientRequest;
import com.github.marquessouza.suportetecnico.application.dto.UpdateClientRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateClientUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.FindClientByIdUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.ListClientsUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.UpdateClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("clients")
@Tag(name = "Clients", description = "gerenciamento de clientes")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final UpdateClientUseCase updateClientUseCase;

    public ClientController(CreateClientUseCase createClientUseCase, FindClientByIdUseCase findClientByIdUseCase, ListClientsUseCase listClientsUseCase, UpdateClientUseCase updateClientUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.findClientByIdUseCase = findClientByIdUseCase;
        this.listClientsUseCase = listClientsUseCase;
        this.updateClientUseCase = updateClientUseCase;
    }

    @Operation(summary = "Cadastra um cliente")
    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request){
        ClientResponse response = createClientUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findClientByIdUseCase.execute(id));

    }

    @Operation(summary = "Lista os clientes")
    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(){
        return ResponseEntity.ok(listClientsUseCase.execute());
    }

    @Operation(summary = "Atualiza um cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateClientRequest request){
        return  ResponseEntity.ok(updateClientUseCase.execute(id, request));
    }
}
