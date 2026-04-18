package com.example.backend.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EJBConfig {
	
    @Bean
    public Context context() throws Exception {
        Properties props = new Properties();

        props.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory");

        props.put(Context.PROVIDER_URL,
                "http-remoting://localhost:8080");

        props.put("jboss.naming.client.ejb.context", true);

        return new InitialContext(props);
    }
}