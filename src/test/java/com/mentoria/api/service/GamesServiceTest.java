package com.mentoria.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.api.DTO.GamesDTO;
import com.mentoria.api.model.Games;
import com.mentoria.api.repository.GamesRepository;

import java.util.*;

public class GamesServiceTest {

    @Mock
    private GamesRepository repository;

    @InjectMocks
    private GamesService gamesService;

    @Mock
    private UtilService utilService;

    private List<Games> gamesList;

    private Games game;

    private GamesDTO gamesDTOs;

    @BeforeEach
    void setup(){
        gamesDTOs = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");
        MockitoAnnotations.openMocks(this);
        gamesList = Arrays.asList(
            new Games(gamesDTOs),
            new Games(gamesDTOs)
        );

        game = new Games(gamesDTOs);
        game.setId(1);
    }

    @Test
    void testSave_success() {
        GamesDTO gamesDTO = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");
        Games games = new Games(gamesDTO);

        when(repository.save(any(Games.class))).thenReturn(games);
        when(utilService.validateData(gamesDTO)).thenReturn(true);

        Games savedGame = gamesService.save(gamesDTO);

        assertNotNull(savedGame);
        assertEquals(games, savedGame);
        verify(repository, times(1)).save(any(Games.class));    
    }

    @Test
    void testSave_withNullDTO() {

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.save(null);
        });

        verify(repository, never()).save(any(Games.class));
    }

    @Test
    void testSave_withNullName() {
        
        GamesDTO gamesDTO = new GamesDTO(null, "teste2", "teste3", "2024-05-06");

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.save(gamesDTO);
        });

        verify(repository, never()).save(any(Games.class));
    }

    @Test
    void testSave_withNullDescription() {
        
        GamesDTO gamesDTO = new GamesDTO("teste", null, "teste3", "2024-05-06");

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.save(gamesDTO);
        });

        verify(repository, never()).save(any(Games.class));
    }

    @Test
    void testSave_withNullPlataform() {
        
        GamesDTO gamesDTO = new GamesDTO("teste", "teste2", null, "2024-05-06");

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.save(gamesDTO);
        });

        verify(repository, never()).save(any(Games.class));
    } 
    void testListAll_WithNoGames_ShouldReturnEmptyList(){
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Games> result = gamesService.listAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListAll_WhenRepositoryThrowsException_ShouldHandleError() {
        // Arrange
        when(repository.findAll()).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> gamesService.listAll());
    }
    
    @Test
    void testSearchById_ShouldReturnGame_WhenFound() {
        when(repository.findById(1)).thenReturn(Optional.of(game));

        Games result = gamesService.searchById(1);

        assertNotNull(result);
        assertEquals(game, result);
        assertEquals(1, result.getId());
    }

    @Test
    void testSearchById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.searchById(1);
        });
    }

    @Test
    void testUpdate_ThrowsExceptionWhenGameNotFound(){

        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.update(1, gamesDTOs);
        });
    }

    @Test
    void testUpdate_UpdatesReleaseDateWhenAllFieldsAreValid(){
        GamesDTO gamesDTO = new GamesDTO("alterado", "alterado", "alterado", "2024-05-07");

        when(repository.findById(1)).thenReturn(Optional.of(game));
        
        when(repository.save(any(Games.class))).thenReturn(game);


        Games gameUpdate = gamesService.update(1, gamesDTO);

        assertNotNull(gameUpdate);
        assertEquals(gamesDTO.title(), gameUpdate.getTitle());
        assertEquals(gamesDTO.genre(), gameUpdate.getGenre());
        assertEquals(gamesDTO.plataform(), gameUpdate.getPlataform());
        assertEquals(gamesDTO.releaseDate(), gameUpdate.getReleaseDate().toString());
    }

    @Test
    void testUpdate_UpdatesReleaseDateWhenTitleGenreAndPlataformAreEmpty(){
        GamesDTO gamesDTO = new GamesDTO("", "", "", "2024-05-08");

        when(repository.findById(1)).thenReturn(Optional.of(game));
        
        when(repository.save(any(Games.class))).thenReturn(game);


        Games gameUpdate = gamesService.update(1, gamesDTO);

        assertNotNull(gameUpdate);
        assertEquals(game.getTitle(), gameUpdate.getTitle());
        assertEquals(game.getGenre(), gameUpdate.getGenre());
        assertEquals(game.getPlataform(), gameUpdate.getPlataform());
        assertEquals(gamesDTO.releaseDate(), gameUpdate.getReleaseDate().toString());
    }

    @Test
    void testUpdate_UpdatesReleaseDateWhenPlataformIsUpdated(){
        GamesDTO gamesDTO = new GamesDTO("", "", "alterado", "");

        when(repository.findById(1)).thenReturn(Optional.of(game));
        
        when(repository.save(any(Games.class))).thenReturn(game);


        Games gameUpdate = gamesService.update(1, gamesDTO);

        assertNotNull(gameUpdate);
        assertEquals(game.getTitle(), gameUpdate.getTitle());
        assertEquals(game.getGenre(), gameUpdate.getGenre());
        assertEquals(gamesDTO.plataform(), gameUpdate.getPlataform());
        assertEquals(game.getReleaseDate(), gameUpdate.getReleaseDate());
    }

    @Test
    void testUpdate_UpdatesReleaseDateWhenGenreIsUpdated(){
        GamesDTO gamesDTO = new GamesDTO("", "alterado", "", "");

        when(repository.findById(1)).thenReturn(Optional.of(game));
        
        when(repository.save(any(Games.class))).thenReturn(game);


        Games gameUpdate = gamesService.update(1, gamesDTO);

        assertNotNull(gameUpdate);
        assertEquals(game.getTitle(), gameUpdate.getTitle());
        assertEquals(gamesDTO.genre(), gameUpdate.getGenre());
        assertEquals(game.getPlataform(), gameUpdate.getPlataform());
        assertEquals(game.getReleaseDate(), gameUpdate.getReleaseDate());
    }

    @Test
    void testUpdate_UpdatesReleaseDateWhenTitleIsUpdated(){
        GamesDTO gamesDTO = new GamesDTO("alterado", "", "", "");

        when(repository.findById(1)).thenReturn(Optional.of(game));
        
        when(repository.save(any(Games.class))).thenReturn(game);


        Games gameUpdate = gamesService.update(1, gamesDTO);

        assertNotNull(gameUpdate);
        assertEquals(gamesDTO.title(), gameUpdate.getTitle());
        assertEquals(game.getGenre(), gameUpdate.getGenre());
        assertEquals(game.getPlataform(), gameUpdate.getPlataform());
        assertEquals(game.getReleaseDate(), gameUpdate.getReleaseDate());
    }

    @Test
    void testDelete_ThrowsExceptionWhenIdIsNull(){

        assertThrows(ResponseStatusException.class, () -> {
            gamesService.delete(null);
        });
    }

    @Test
    void testDelete_DeletesGameSuccessfullyWhenIdExists(){
        when(repository.existsById(1)).thenReturn(true);

        gamesService.delete(1);

        verify(repository, times(1)).deleteById(1);
    }

}