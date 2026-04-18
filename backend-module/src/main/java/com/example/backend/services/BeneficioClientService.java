package com.example.backend.services;

import org.springframework.stereotype.Service;

import com.example.ejb.model.Beneficio;
import com.example.ejb.remote.BeneficioEjbRemote;

@Service
public class BeneficioClientService {
	
	private final BeneficioEjbRemote service;

	public BeneficioClientService(BeneficioEjbRemote service) {
        this.service = service;
    }
	
	public String getHelloWorld() {
		return service.getHelloWorld();
	}
	
	public Beneficio findById(Long id) {
		return service.findById(id);
	}

}
