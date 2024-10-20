package com.mentoria.api.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.repository.GamesRepository;

@Service
public class GamesService {
    
    @Autowired
    private GamesRepository repository;

    public Games save(GamesDTO data){ 
        if(data != null && validateData(data)){
            Games games = new Games(data);
            return repository.save(games);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Games> listAll(){
        return repository.findAll();
    }

    public Games searchById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Games update(Integer id, GamesDTO data){
        Games games = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (games != null) {
            
            games.setTitle(!data.title().isEmpty() ? data.title() : games.getTitle());

            games.setGenre(!data.genre().isEmpty()  ? data.genre() : games.getGenre());
            
            games.setPlataform(!data.plataform().isEmpty()  ? data.plataform() : games.getPlataform());
            
            games.setReleaseDate(!data.releaseDate().isEmpty() ? Date.valueOf(data.releaseDate()) : games.getReleaseDate());

            return repository.save(games);
        } 

        return games;

    }

    public void delete(Integer id){
        if(id != null){
            repository.deleteById(id);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Boolean validateData(GamesDTO data){

        if (data == null) {
            return false;
        }
        
        return !(data.title() == null || data.title().isEmpty() ||
        data.genre() == null || data.genre().isEmpty() ||
        data.plataform() == null || data.plataform().isEmpty() ||
        data.releaseDate() == null || data.releaseDate().isEmpty());
    }
}