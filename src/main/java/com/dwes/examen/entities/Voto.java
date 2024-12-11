package com.dwes.examen.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaVoto;

    @ManyToOne(targetEntity = Aspirante.class)
    private Aspirante aspirante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(LocalDateTime fechaVoto) {
        this.fechaVoto = fechaVoto;
    }

    public Aspirante getAspirante() {
        return aspirante;
    }

    public void setAspirante(Aspirante aspirante) {
        this.aspirante = aspirante;
    }
}
