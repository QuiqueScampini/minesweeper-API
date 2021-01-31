package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

	@Autowired
	private BoardFactory boardFactory;

	public Game create(GameRequest gameRequest) {
		String user = gameRequest.getUser();
		return new Game.Builder()
				.withUser(user)
				.withBoard(boardFactory.create(gameRequest))
				.withLeftFlags(gameRequest.getMines())
				.build();
	}
}
