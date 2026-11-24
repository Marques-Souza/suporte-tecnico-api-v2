package com.github.marquessouza.suportetecnico.domain.model;

import java.util.UUID;

public class Technician {

    private final UUID id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final boolean active;

    public Technician(UUID id, String name, String cpf, String phone, boolean active) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }
}
