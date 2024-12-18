package com.dwes.examen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "votos")
@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaVoto;

    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(targetEntity = Aspirante.class)
    private Aspirante aspirante;

}
