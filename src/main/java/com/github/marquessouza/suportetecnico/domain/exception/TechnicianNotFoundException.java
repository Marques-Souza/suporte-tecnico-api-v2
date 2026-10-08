package com.github.marquessouza.suportetecnico.domain.exception;

public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException() {
        super("Technician not found");
    }
}
