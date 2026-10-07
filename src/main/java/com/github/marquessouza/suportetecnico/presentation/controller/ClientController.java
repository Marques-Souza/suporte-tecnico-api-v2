package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.CreateClientRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateClientUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.FindClientByIdUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.ListClientsUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;
    private final ListClientsUseCase listClientsUseCase;

    public ClientController(CreateClientUseCase createClientUseCase, FindClientByIdUseCase findClientByIdUseCase, ListClientsUseCase listClientsUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.findClientByIdUseCase = findClientByIdUseCase;
        this.listClientsUseCase = listClientsUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request){
        ClientResponse response = createClientUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findClientByIdUseCase.execute(id));

    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(){
        return ResponseEntity.ok(listClientsUseCase.execute());
    }
}
