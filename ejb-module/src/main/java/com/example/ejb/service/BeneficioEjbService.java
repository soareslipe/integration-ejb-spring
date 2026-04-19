package com.example.ejb.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.model.Beneficio;
import com.example.ejb.remote.BeneficioEjbRemote;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
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

        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs não podem ser nulos.");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Transferência para a mesma conta não é permitida.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero.");
        }

        try {
            Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
            Beneficio to   = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

            if (from == null || to == null) {
                throw new RuntimeException("Conta não encontrada.");
            }

            if (Boolean.FALSE.equals(from.getAtivo()) || Boolean.FALSE.equals(to.getAtivo())) {
                throw new RuntimeException("Conta inativa.");
            }

            if (from.getValor().compareTo(amount) < 0) {
                throw new RuntimeException("Saldo insuficiente.");
            }


            from.setValor(from.getValor().subtract(amount));
            to.setValor(to.getValor().add(amount));

            em.flush();

        } catch (Exception e) {
            throw new RuntimeException("Erro na transferência: " + e.getMessage(), e);
        }
    }

    @Override
    public String getHelloWorld() {
        return "Hello Stateful World";
    }
    
}
