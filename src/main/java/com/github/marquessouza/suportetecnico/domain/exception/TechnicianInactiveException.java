package com.github.marquessouza.suportetecnico.domain.exception;

public class TechnicianInactiveException extends RuntimeException {
    public TechnicianInactiveException() {
        super("Technician is inactive");
    }
}
