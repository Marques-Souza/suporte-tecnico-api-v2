package com.github.marquessouza.suportetecnico.domain.exception;

public class TechnicianAlreadyExistsException extends RuntimeException {

    public TechnicianAlreadyExistsException(){
        super("A technician with this CPF is already registered");
    }
}
