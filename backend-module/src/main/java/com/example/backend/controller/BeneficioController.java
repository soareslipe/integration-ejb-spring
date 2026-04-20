package com.example.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.BeneficioClientService;
import com.example.ejb.model.Beneficio;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioClientService service;

    public BeneficioController(BeneficioClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Beneficio>> listar() {
    	List<Beneficio> lista = service.findAll();
    	return ResponseEntity.ok(lista);
    }
    
    @PostMapping
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        Beneficio criado = service.create(beneficio);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscarPorId(@PathVariable Long id) {
        Beneficio ben = service.findById(id);

        if (ben == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ben);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Beneficio beneficio) {

        try {
            beneficio.setId(id); // garante consistência

            Beneficio atualizado = service.update(beneficio);
            return ResponseEntity.ok(atualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferir(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam BigDecimal amount) {

        try {
            service.transfer(fromId, toId, amount);
            return ResponseEntity.ok("Transferência realizada com sucesso.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}