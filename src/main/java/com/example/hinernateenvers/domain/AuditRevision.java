package com.example.hinernateenvers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name = "revinfo")
@RevisionEntity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuditRevision extends DefaultRevisionEntity {
}
