package com.example.ejb.remote;

import java.math.BigDecimal;

import com.example.ejb.model.Beneficio;

import jakarta.ejb.Remote;

@Remote
public interface BeneficioEjbRemote {

	String getHelloWorld();

	void transfer(Long fromId, Long toId, BigDecimal amount);

	Beneficio findById(Long id);

}
