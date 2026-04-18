package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ejb.remote.BeneficioEjbRemote;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {
	
	private final BeneficioEjbRemote service;

	public BeneficioController(BeneficioEjbRemote service) {
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
    
}
