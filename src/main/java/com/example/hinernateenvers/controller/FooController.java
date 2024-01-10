package com.example.hinernateenvers.controller;

import com.example.hinernateenvers.domain.Foo;
import com.example.hinernateenvers.service.FooServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foo")
public class FooController {

    @Autowired
    private FooServiceImpl fooService;

    @PostMapping
    public Foo add(@RequestBody Foo foo) {
        return fooService.save(foo);
    }

    @GetMapping
    public List<Foo> findAll() {
        return fooService.findAll();
    }

    @GetMapping("/{id}")
    public Foo get(@PathVariable("id") long id) {
        return fooService.findById(id);
    }
}
