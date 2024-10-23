package com.mentoria.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.service.GamesService;
import com.mentoria.api.service.UtilService;


@RestController
@RequestMapping("/api/games")
public class GamesConttroller {

    @Autowired
    private GamesService service;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody GamesDTO data){
        try {
            service.save(data);
            return ResponseEntity.status(201).build();

        } catch (ResponseStatusException e) {
           return ResponseEntity.badRequest().build();
        }
        
    }

    @GetMapping("/list")
    public ResponseEntity<List> listAll(){
        List<Games> list = service.listAll();
        return ResponseEntity.ok(list);
    }
   
    @GetMapping("/list/{id}")
    public ResponseEntity<Games> searchById(@PathVariable Integer id){
        try {
            Games game = service.searchById(id);
            return ResponseEntity.ok(game);   
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody GamesDTO data){
        try {
            service.update(id, data);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try {
            service.delete(id);
            return ResponseEntity.ok().build();   
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
