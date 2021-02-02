package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.Action;
import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.domain.action.FlagAction;
import com.deviget.minesweeper.domain.action.GameAction;
import com.deviget.minesweeper.domain.action.RevealAction;
import com.deviget.minesweeper.domain.domain2api.GameResponseTransformer;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import com.deviget.minesweeper.domain.factory.GameFactory;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

import static com.deviget.minesweeper.api.request.Action.FLAG;
import static com.deviget.minesweeper.api.request.Action.REVEAL;
import static com.deviget.minesweeper.model.GameStatus.PAUSED;

@Service
public class GameService {

	@Autowired
	private GameFactory gameFactory;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GameResponseTransformer gameResponseTransformer;

	private final EnumMap<Action, GameAction> actionsMap = new EnumMap<>(Action.class);

	@Autowired
	public GameService(FlagAction flagAction, RevealAction revealAction) {
		actionsMap.put(FLAG,flagAction);
		actionsMap.put(REVEAL,revealAction);
	}

	public GameResponse createGame(GameRequest gameRequest) {
		Game game = gameFactory.create(gameRequest);
		return this.saveAndGenerateResponse(game);
	}

	public GameResponse pauseGame(int id) {
		Game game = this.findGame(id);
		game.setStatus(PAUSED);
		return this.saveAndGenerateResponse(game);
	}

	public GameResponse executeAction(int id, ActionRequest actionRequest) {
		Game game = this.findGame(id);
		GameAction action = actionsMap.get(actionRequest.getAction());
		action.execute(actionRequest, game);

		return this.saveAndGenerateResponse(game);
	}

	private GameResponse saveAndGenerateResponse(Game game) {
		gameRepository.save(game);
		return gameResponseTransformer.transform(game);
	}

	private Game findGame(int id) {
		return gameRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Game with Id: " + id + " not found"));
	}
}
