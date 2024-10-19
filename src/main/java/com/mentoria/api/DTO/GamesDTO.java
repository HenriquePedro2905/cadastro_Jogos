package com.mentoria.api.DTO;

import java.sql.Date;

public record GamesDTO(String title,
                       String genre,
                       String plataform,
                       Date releaseDate) {}
