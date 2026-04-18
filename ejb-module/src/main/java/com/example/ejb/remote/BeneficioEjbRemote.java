package com.example.ejb.remote;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.model.Beneficio;

import jakarta.ejb.Remote;

@Remote
public interface BeneficioEjbRemote {

	 Beneficio create(Beneficio beneficio);

	    Beneficio findById(Long id);

	    List<Beneficio> findAll();

	    Beneficio update(Beneficio beneficio);

	    void delete(Long id);

	    void transfer(Long fromId, Long toId, BigDecimal amount);

	    String getHelloWorld();

}
