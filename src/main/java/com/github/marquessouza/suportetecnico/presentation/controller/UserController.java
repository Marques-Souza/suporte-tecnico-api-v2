package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.CreateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UpdateUserPasswordRequest;
import com.github.marquessouza.suportetecnico.application.dto.UpdateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.application.usecase.*;
import com.github.marquessouza.suportetecnico.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "gerenciamento de usuários")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    public UserController(CreateUserUseCase createUserUseCase, FindUserByIdUseCase findUserByIdUseCase, ListUsersUseCase listUsersUseCase, UpdateUserUseCase updateUserUseCase, UpdateUserPasswordUseCase updateUserPasswordUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
    }

    @PostMapping
    @Operation(summary = "Cadastra um usuário")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request){
        UserResponse response = createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findUserByIdUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Lista os usuários")
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(listUsersUseCase.execute());
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(updateUserUseCase.execute(id, request));

    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Atualiza a senha de um usuário")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @Valid @RequestBody UpdateUserPasswordRequest request){
        updateUserPasswordUseCase.execute(id, request);
        return ResponseEntity.noContent().build();

    }
}
