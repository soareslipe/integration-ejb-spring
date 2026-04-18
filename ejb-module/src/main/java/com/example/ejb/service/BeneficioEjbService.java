package com.example.ejb.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.model.Beneficio;
import com.example.ejb.remote.BeneficioEjbRemote;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;

@Stateless(name = "BeneficioEjbRemote")
public class BeneficioEjbService implements BeneficioEjbRemote {

	@PersistenceContext
    private EntityManager em;

    @Override
    public Beneficio create(Beneficio beneficio) {
        em.persist(beneficio);
        return beneficio;
    }

    @Override
    public Beneficio findById(Long id) {
        return em.find(Beneficio.class, id);
    }

    @Override
    public List<Beneficio> findAll() {
        return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
                 .getResultList();
    }


    @Override
    public Beneficio update(Beneficio beneficio) {
        try {
            return em.merge(beneficio);
        } catch (OptimisticLockException e) {
            throw new RuntimeException(
                "Erro de concorrência: o registro foi alterado por outro usuário."
            );
        }
    }

    @Override
    public void delete(Long id) {
        Beneficio beneficio = em.find(Beneficio.class, id);

        if (beneficio == null) {
            throw new RuntimeException("Registro não encontrado para exclusão.");
        }

        try {
            em.remove(beneficio);
        } catch (OptimisticLockException e) {
            throw new RuntimeException(
                "Erro de concorrência: o registro foi alterado antes da exclusão."
            );
        }
    }

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
    public String getHelloWorld() {
        return "Hello Stateful World";
    }
    
}
