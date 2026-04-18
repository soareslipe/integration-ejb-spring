package com.example.backend.services;

import java.math.BigDecimal;
import java.util.List;

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

    public Beneficio create(Beneficio beneficio) {
        return service.create(beneficio);
    }

    public Beneficio findById(Long id) {
        return service.findById(id);
    }

    public List<Beneficio> findAll() {
        return service.findAll();
    }

    public Beneficio update(Beneficio beneficio) {
        return service.update(beneficio);
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        service.transfer(fromId, toId, amount);
    }
}