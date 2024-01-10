package com.example.hinernateenvers.service;

import com.example.hinernateenvers.domain.Bar;
import com.example.hinernateenvers.repository.BarRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarServiceImpl {

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private AuditReader auditReader;

    public Bar save(Bar bar) {
        return barRepository.save(bar);
    }

    public List<Bar> findAll() {
        return barRepository.findAll();
    }

    public Bar findById(Long id) {
        return barRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<?> getRevisions(long id) {
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Bar.class, true);
        auditQuery.add(AuditEntity.id().eq(id));
        return auditQuery.getResultList();
    }

    public List<?> getRevisionsWithChanges(long id) {
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(Bar.class, true);
        auditQuery.add(AuditEntity.id().eq(id));
        return auditQuery.getResultList();
    }
}
