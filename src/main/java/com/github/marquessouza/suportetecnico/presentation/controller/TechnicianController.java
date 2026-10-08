package com.github.marquessouza.suportetecnico.presentation.controller;

import com.github.marquessouza.suportetecnico.application.dto.CreateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateTechnicianUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.FindTechnicianByIdUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.ListTechniciansUseCase;
import com.github.marquessouza.suportetecnico.application.usecase.UpdateTechnicianUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/technicians")
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
    public ResponseEntity<TechnicianResponse> create(@Valid @RequestBody CreateTechnicianRequest request){
        TechnicianResponse response = createTechnicianUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(findTechnicianByIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<TechnicianResponse>> findAll(){
        return ResponseEntity.ok(listTechniciansUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnicianResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTechnicianRequest request){
        return ResponseEntity.ok(updateTechnicianUseCase.execute(id, request));
    }
}
