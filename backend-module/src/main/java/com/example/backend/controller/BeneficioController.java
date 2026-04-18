package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

//	@GetMapping
//	public List<String> list() {
//		return Arrays.asList("Beneficio A", "Beneficio B");
//	}
    
    @GetMapping
    public String hello() {
        return service.getHelloWorld();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscarPorId(@PathVariable Long id) {
    	Beneficio ben = service.findById(id);
        return ResponseEntity.ok().body(ben);
    }
    
}
