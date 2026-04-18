package com.example.ejb.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

import com.example.ejb.model.Beneficio;
import com.example.ejb.remote.BeneficioEjbRemote;

@Stateless(name = "BeneficioEjbRemote")
public class BeneficioEjbService implements BeneficioEjbRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Beneficio from = em.find(Beneficio.class, fromId);
        Beneficio to   = em.find(Beneficio.class, toId);

        // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
    
    @Override
    public Beneficio findById(Long id) {
        return em.find(Beneficio.class, id);
    }

    @Override
    public String getHelloWorld() {
        return "Hello Stateful World";
    }
}
