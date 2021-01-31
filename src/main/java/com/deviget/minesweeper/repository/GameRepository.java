package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game,Integer> {
}
