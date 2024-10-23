package com.mentoria.api.controller;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.service.GamesService;
import com.mentoria.api.service.UtilService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class GamesControllerTest {

    @InjectMocks
    private GamesConttroller controller;  

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GamesService service;

    @Mock
    private UtilService utilService;

    private List<Games> gamesList;

    private GamesDTO gamesDTOs;

    @BeforeEach
    void setUp() {
        gamesDTOs = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");
       
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller).build();  

        gamesList = Arrays.asList(
            new Games(gamesDTOs),
            new Games(gamesDTOs)
        );
    }

    @Test
    void testGamesControllerCreateGame() throws Exception {
        GamesDTO gamesDTO = new GamesDTO("Game Title", "Action", "PC", "2024-01-01");


        mockMvc.perform(post("/api/games/save")
                .contentType(MediaType.APPLICATION_JSON)  
                .content("{\"title\":\"Game Title\", \"genre\":\"Action\", \"plataform\":\"PC\", \"releaseDate\":\"2024-01-01\"}")) 
                .andExpect(status().isCreated());  

        verify(service, times(1)).save(gamesDTO);
    }

    @Test
    void testGamesControllerCreateGameWithInvalidData() throws Exception {
        GamesDTO gamesDTO = new GamesDTO("", "Action", "PC", "2024-01-01");
        when(service.save(gamesDTO)).thenThrow(ResponseStatusException.class);

        mockMvc.perform(post("/api/games/save")
                .contentType(MediaType.APPLICATION_JSON)  
                .content("{\"title\":\"\", \"genre\":\"Action\", \"plataform\":\"PC\", \"releaseDate\":\"2024-01-01\"}")) 
                .andExpect(status().isBadRequest());  

        verify(service, times(1)).save(gamesDTO);
    }

    @Test
    void testGamesControllerGetAllGames() throws Exception{

        when(service.listAll()).thenReturn(gamesList);

        mockMvc.perform(get("/api/games/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGamesControllerGetGameById() throws Exception{
        Games games = new Games(gamesDTOs);

        when(service.searchById(1)).thenReturn(games);

        mockMvc.perform(get("/api/games/list/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGamesControllerGetGameByIdNotFound() throws Exception{

        when(service.searchById(1)).thenThrow(ResponseStatusException.class);

        mockMvc.perform(get("/api/games/list/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGamesControllerUpdateGame() throws Exception {
        GamesDTO gamesDTO = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");
        Games games = new Games(gamesDTO);

        when(service.update(1,gamesDTOs)).thenReturn(games);
        
        mockMvc.perform(
                put("/api/games//update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"teste\", \"genre\":\"teste2\", \"plataform\":\"teste3\", \"releaseDate\":\"2024-05-06\"}")
                )
                .andExpect(status().isOk());

        verify(service, times(1)).update(1, gamesDTO);
    }

    @Test
    void testGamesControllerUpdateGameNotFound() throws Exception {
        GamesDTO gamesDTO = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");

        when(service.update(1,gamesDTOs)).thenThrow(ResponseStatusException.class);
        
        mockMvc.perform(
                put("/api/games//update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"teste\", \"genre\":\"teste2\", \"plataform\":\"teste3\", \"releaseDate\":\"2024-05-06\"}")
                )
                .andExpect(status().isNotFound());

        verify(service, times(1)).update(1, gamesDTO);
    }

    @Test
    void testGamesControllerDeleteGame() throws Exception{

        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/api/games/delete/{id}", 1))
                .andExpect(status().isOk());
                
        verify(service, times(1)).delete(1);
    }

    @Test
    void testGamesControllerDeleteGameBadRequest() throws Exception{

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(service).delete(1);

        mockMvc.perform(delete("/api/games/delete/{id}", 1))
                .andExpect(status().isBadRequest());
                
        verify(service, times(1)).delete(1);
    }

    
}
