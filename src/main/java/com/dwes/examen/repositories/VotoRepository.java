package com.dwes.examen.repositories;

import com.dwes.examen.entities.Aspirante;
import com.dwes.examen.entities.Usuario;
import com.dwes.examen.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    //List<Voto> findAllByUsuario(Usuario usuario);
}
