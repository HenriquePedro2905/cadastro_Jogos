package com.mentoria.api.service;

import org.springframework.stereotype.Service;

import com.mentoria.api.DTO.GamesDTO;

@Service
public class UtilService {

    public Boolean validateData(GamesDTO data){

        if (data == null) {
            return false;
        }
        
        return !(data.title() == null || data.title().isEmpty() ||
        data.genre() == null || data.genre().isEmpty() ||
        data.plataform() == null || data.plataform().isEmpty() ||
        data.releaseDate() == null || data.releaseDate().isEmpty());
    }
}
