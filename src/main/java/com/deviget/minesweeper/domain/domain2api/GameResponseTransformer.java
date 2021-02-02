package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.domain.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameResponseTransformer {

	@Autowired
	private CellsResponseTransformer cellsResponseTransformer;

	@Autowired
	private GameTimeResolver gameTimeResolver;

	public GamesResponse transform(List<Game> games, String user) {
		List<GameResponse> gameResponses = games.stream()
				.map(this::transform)
				.collect(Collectors.toList());
		return new GamesResponse(gameResponses,user);
	}

	public GameResponse transform(Game game) {
		return new GameResponse.Builder()
				.withId(game.getId())
				.withUser(game.getUser())
				.withStatus(game.getStatus())
				.withBoard(this.cellsResponseTransformer.transform(game.getBoard()))
				.withGameTime(this.gameTimeResolver.resolve(game))
				.withLeftFlags(game.getLeftFlags())
				.build();
	}
}
