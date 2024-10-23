package com.mentoria.api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.mentoria.api.DTO.GamesDTO;

public class UtilServiceTest {

    @InjectMocks
    private UtilService utilService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateDataAllValid() {
        GamesDTO gamesDTO = new GamesDTO("teste", "teste2", "teste3", "2024-05-06");
        boolean validateDataTest = utilService.validateData(gamesDTO);
    
        assertTrue(validateDataTest);
    }
    
    @Test
    void testValidateDataNullDTO() {
        boolean validateDataTest = utilService.validateData(null);
    
        assertFalse(validateDataTest);
    }
    
    @Test
    void testValidateDataNameNull() {
        GamesDTO gamesDTO = new GamesDTO(null, "teste2", "teste3", "2024-05-06");
        boolean validateDataTest = utilService.validateData(gamesDTO);
    
        assertFalse(validateDataTest);
    }
    
    @Test
    void testValidateDataNameEmpty() {
        GamesDTO gamesDTO = new GamesDTO("", "teste2", "teste3", "2024-05-06");
        boolean validateDataTest = utilService.validateData(gamesDTO);
    
        assertFalse(validateDataTest);
    }
    

    @Test
void testValidateDataTitleNull() {
    GamesDTO gamesDTO = new GamesDTO("teste1", null, "teste3", "2024-05-06");
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

@Test
void testValidateDataTitleEmpty() {
    GamesDTO gamesDTO = new GamesDTO("teste1", "", "teste3", "2024-05-06");
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

@Test
void testValidateDataGenreNull() {
    GamesDTO gamesDTO = new GamesDTO("teste1", "teste2", null, "2024-05-06");
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

@Test
void testValidateDataGenreEmpty() {
    GamesDTO gamesDTO = new GamesDTO("teste1", "teste2", "", "2024-05-06");
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

@Test
void testValidateDataReleaseDateNull() {
    GamesDTO gamesDTO = new GamesDTO("teste1", "teste2", "teste3", null);
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

@Test
void testValidateDataReleaseDateEmpty() {
    GamesDTO gamesDTO = new GamesDTO("teste1", "teste2", "teste3", "");
    boolean validateDataTest = utilService.validateData(gamesDTO);

    assertFalse(validateDataTest);
}

}
