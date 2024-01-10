package com.example.hinernateenvers.controller;

import com.example.hinernateenvers.domain.Bar;
import com.example.hinernateenvers.service.BarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bar")
public class BarController {

    @Autowired
    private BarServiceImpl barService;

    @PostMapping
    public Bar add(@RequestBody Bar bar) {
        return barService.save(bar);
    }

    @GetMapping
    public List<Bar> findAll() {
        return barService.findAll();
    }

    @GetMapping("/{id}")
    public Bar get(@PathVariable("id") Long id) {
        return barService.findById(id);
    }

    @GetMapping(path = "/{id}/revisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List revisions(@PathVariable("id") Long id) {
        return barService.getRevisions(id);
    }

    @GetMapping("/{id}/revisions_with_changes")
    public List revisionsWithChanges(@PathVariable("id") Long id) {
        return barService.getRevisionsWithChanges(id);
    }
}
