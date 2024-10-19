package com.mentoria.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentoria.api.model.Games;

public interface GamesRepository extends JpaRepository<Games, Integer>{

}
