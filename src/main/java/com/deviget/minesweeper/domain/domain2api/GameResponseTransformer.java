package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.domain.service.GameOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameResponseTransformer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameResponseTransformer.class);

	@Autowired
	private CellsResponseTransformer cellsResponseTransformer;

	@Autowired
	private GameOperation gameOperation;

	public GamesResponse transform(List<Game> games, String user) {
		List<GameResponse> gameResponses = games.stream()
				.map(this::transform)
				.collect(Collectors.toList());
		return new GamesResponse(gameResponses,user);
	}

	public GameResponse transform(Game game) {
		LOGGER.debug("Generating Game {} response",game.getId());
		return new GameResponse.Builder()
				.withId(game.getId())
				.withUser(game.getUser())
				.withStatus(game.getStatus())
				.withBoard(this.cellsResponseTransformer.transform(game.getBoard()))
				.withGameTime(this.gameOperation.calculateGameTime(game))
				.withLeftFlags(game.getLeftFlags())
				.build();
	}
}
