package com.deviget.minesweeper.service;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public String createGame(GameRequest gameRequest, String user) {
		return null;
	}
}
