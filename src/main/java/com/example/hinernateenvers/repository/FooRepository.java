package com.example.hinernateenvers.repository;

import com.example.hinernateenvers.domain.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
