package com.mentoria.api.model;

import java.sql.Date;

import com.mentoria.api.DTO.GamesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Games {

    public Games(GamesDTO dt){
        this.title = dt.title();
        this.genre = dt.genre();
        this.plataform = dt.plataform();
        this.releaseDate = Date.valueOf(dt.releaseDate());
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String genre;

    private String plataform;

    @Column(name = "release_date")
    private Date releaseDate;
}
