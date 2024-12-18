package com.dwes.examen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String rol;
    private String foto;


    @OneToMany(targetEntity = Voto.class, mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Voto> votos = new ArrayList<>();

}
