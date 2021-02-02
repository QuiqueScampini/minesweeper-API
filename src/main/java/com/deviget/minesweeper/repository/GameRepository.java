package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.domain.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game,Integer> {

	@Override
	List<Game> findAll();

	List<Game> findAllByUser(String user);

}
