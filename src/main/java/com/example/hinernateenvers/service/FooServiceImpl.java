package com.example.hinernateenvers.service;

import com.example.hinernateenvers.domain.Foo;
import com.example.hinernateenvers.repository.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FooServiceImpl {

    @Autowired
    private FooRepository fooRepository;

    public Foo save(Foo foo) {
        return fooRepository.save(foo);
    }

    public List<Foo> findAll() {
        return fooRepository.findAll();
    }

    public Foo findById(long id) {
        return fooRepository.findById(id).orElseThrow();
    }
}
