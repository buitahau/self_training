package com.example.hinernateenvers.config;

import com.example.hinernateenvers.config.jpa.AuditAwareImpl;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditProvider")
public class JpaConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public AuditorAware<String> customAuditProvider() {
        return new AuditAwareImpl();
    }

    @Bean
    public AuditReader auditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }
}
