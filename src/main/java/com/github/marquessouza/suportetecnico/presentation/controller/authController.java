package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.LoginRequest;
import com.github.marquessouza.suportetecnico.application.dto.LoginResponse;
import com.github.marquessouza.suportetecnico.application.usecase.AuthenticateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class authController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public authController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticateUserUseCase.execute(request));
    }
}
