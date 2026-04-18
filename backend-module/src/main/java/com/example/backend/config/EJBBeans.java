package com.example.backend.config;

import javax.naming.Context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ejb.remote.BeneficioEjbRemote;

@Configuration
public class EJBBeans {
	
	@Bean
    public BeneficioEjbRemote beneficioService(Context context) throws Exception {

        return (BeneficioEjbRemote) context.lookup(
            "ejb:/ejb-module-1.2-SNAPSHOT/BeneficioEjbRemote!com.example.ejb.remote.BeneficioEjbRemote"
        );
    }

}
