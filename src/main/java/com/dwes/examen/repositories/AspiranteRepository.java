package com.dwes.examen.repositories;

import com.dwes.examen.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AspiranteRepository extends JpaRepository<Aspirante, Long> {
}
