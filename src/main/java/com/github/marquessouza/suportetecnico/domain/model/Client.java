package com.github.marquessouza.suportetecnico.domain.model;

import java.util.UUID;

public class Client {

    private final UUID id;
    private final String name;
    private final String cpf;
    private final String phone;


    public Client(UUID id, String name, String cpf, String phone) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
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

    public UUID getId() {
        return id;
    }
}