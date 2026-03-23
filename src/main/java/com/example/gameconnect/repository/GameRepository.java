package com.example.gameconnect.repository;

import com.example.gameconnect.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByGenresIn(List<String> genres);
}
