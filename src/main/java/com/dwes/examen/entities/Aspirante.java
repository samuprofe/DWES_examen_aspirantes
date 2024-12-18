package com.dwes.examen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="aspirantes")
@Entity
public class Aspirante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El nombre no puede estar en blanco")
    private String nombre;
    @NotEmpty(message = "El apellido no puede estar en blanco")
    private String apellidos;
    private String foto;

    @OneToMany(targetEntity = Voto.class, mappedBy = "aspirante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Voto> votos = new ArrayList<>();

}
