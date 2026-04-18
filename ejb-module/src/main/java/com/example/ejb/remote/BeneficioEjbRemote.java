package com.example.ejb.remote;

import java.math.BigDecimal;

public interface BeneficioEjbRemote {

	String getHelloWorld();

	void transfer(Long fromId, Long toId, BigDecimal amount);

}
