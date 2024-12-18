package com.dwes.examen.controllers;

import com.dwes.examen.entities.Aspirante;
import com.dwes.examen.repositories.AspiranteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/API")
public class AspirantesAPIController {

    @Autowired
    AspiranteRepository aspiranteRepository;

    @GetMapping("/v1/aspirantes/")
    public List<Aspirante> getAspirante() {
        return aspiranteRepository.findAll();
    }

    @GetMapping("/v1/aspirantes/{id}")
    public Aspirante getAspiranteById(@PathVariable Long id, HttpServletResponse response) {
        return aspiranteRepository.findById(id).orElseThrow(() -> new RuntimeException("aspirante no encontrado"));
    }

    @GetMapping("/v2/aspirantes/")
    public ResponseEntity<List<Aspirante>> getAspiranteV2() {
        List<Aspirante> aspirantes = aspiranteRepository.findAll();
        if(aspirantes.isEmpty()) {
            return ResponseEntity.notFound().build();   //204 No Content
        }else{
            return ResponseEntity.ok(aspirantes);       //200 OK
        }
    }

    @GetMapping("/v2/aspirantes/{id}")
    public ResponseEntity<Aspirante> getAspiranteByIdV2(@PathVariable Long id) {
        var aspirante = aspiranteRepository.findById(id);
        if(aspirante.isPresent()) {
            return ResponseEntity.ok(aspirante.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
