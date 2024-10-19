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

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.service.GamesService;

@RestController
@RequestMapping("api/games")
public class GamesConttroller {

    @Autowired
    private GamesService service;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody GamesDTO data){
        service.save(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List> listAll(){
        List<Games> list = service.listAll();
        return ResponseEntity.ok(list);
    }
   
    @GetMapping("/list/{id}")
    public ResponseEntity<Games> searchById(@PathVariable Integer id){
        Games game = service.searchById(id);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody GamesDTO data){
        service.update(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
