package com.mentoria.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.repository.GamesRepository;

public class GamesService {
    
    @Autowired
    private GamesRepository repository;

    public Games save(GamesDTO data){ 
        Games games = new Games(data);
        return repository.save(games);
    }

    public List<Games> listAll(){
        return repository.findAll();
    }

    public Games searchById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Games update(Integer id, GamesDTO data){
        Games games = repository.findById(id).orElse(null);

        if (games != null) {
            
            games.setTitle(!data.title().isBlank() ? data.title() : games.getTitle());

            games.setGenre(!data.plataform().isBlank() ? data.plataform() : games.getPlataform());
            
            games.setGenre(!data.genre().isBlank() ? data.genre() : games.getGenre());
            
            games.setReleaseDate(data.releaseDate() != null ? data.releaseDate() : games.getReleaseDate());

            return repository.save(games);
        }

        return null;
    }

    public void delete(Integer id){
       repository.deleteById(id);
    }
}
