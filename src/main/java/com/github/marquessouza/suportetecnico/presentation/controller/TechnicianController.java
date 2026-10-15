package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.CreateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateTechnicianUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.FindTechnicianByIdUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.ListTechniciansUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.UpdateTechnicianUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/technicians")
@Tag(name = "Technicians", description = "gerenciamento de técnicos")
public class TechnicianController {

    private final CreateTechnicianUseCase createTechnicianUseCase;
    private final FindTechnicianByIdUseCase findTechnicianByIdUseCase;
    private final ListTechniciansUseCase listTechniciansUseCase;
    private final UpdateTechnicianUseCase updateTechnicianUseCase;

    public TechnicianController(CreateTechnicianUseCase createTechnicianUseCase,
                                FindTechnicianByIdUseCase findTechnicianByIdUseCase,
                                ListTechniciansUseCase listTechniciansUseCase,
                                UpdateTechnicianUseCase updateTechnicianUseCase) {
        this.createTechnicianUseCase = createTechnicianUseCase;
        this.findTechnicianByIdUseCase = findTechnicianByIdUseCase;
        this.listTechniciansUseCase = listTechniciansUseCase;
        this.updateTechnicianUseCase = updateTechnicianUseCase;
    }

    @PostMapping
    @Operation(summary = "Cadastra um técnico")
    public ResponseEntity<TechnicianResponse> create(@Valid @RequestBody CreateTechnicianRequest request){
        TechnicianResponse response = createTechnicianUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar técnico por ID")
    public ResponseEntity<TechnicianResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findTechnicianByIdUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Lista os técnicos")
    public ResponseEntity<List<TechnicianResponse>> findAll(){
        return ResponseEntity.ok(listTechniciansUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um técnico")
    public ResponseEntity<TechnicianResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTechnicianRequest request){
        return ResponseEntity.ok(updateTechnicianUseCase.execute(id, request));
    }
}
