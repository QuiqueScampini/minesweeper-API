package com.deviget.minesweeper.service;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public GameResponse createGame(GameRequest gameRequest) {
		Game newGame = gameRepository.save(new Game(gameRequest.getUser()));
		return new GameResponse(newGame.getId(),newGame.getUser());
	}
}
