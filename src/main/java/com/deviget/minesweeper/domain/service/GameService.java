package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.Action;
import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.domain.action.FlagAction;
import com.deviget.minesweeper.domain.action.GameAction;
import com.deviget.minesweeper.domain.action.RevealAction;
import com.deviget.minesweeper.domain.domain2api.GameResponseTransformer;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import com.deviget.minesweeper.domain.factory.GameFactory;
import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.deviget.minesweeper.api.request.Action.FLAG;
import static com.deviget.minesweeper.api.request.Action.REVEAL;

@Service
public class GameService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

	@Autowired
	private GameOperation gameOperation;

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

	public GamesResponse retrieveGames(String user) {
		List<Game> games = this.findGames(user);
		LOGGER.info("Retrieved {} Games", games.size());
		return gameResponseTransformer.transform(games,user);
	}

	public GameResponse retrieveGame(int id) {
		Game game = this.findGame(id);
		return gameResponseTransformer.transform(game);
	}

	public GameResponse createGame(GameRequest gameRequest) {
		Game game = gameFactory.create(gameRequest);
		GameResponse gameResponse = this.saveAndGenerateResponse(game);
		LOGGER.info("Game with id {} created",game.getId());
		return gameResponse;
	}

	public GameResponse pauseGame(int id) {
		Game game = this.findGame(id);
		gameOperation.pauseGame(game);
		return this.saveAndGenerateResponse(game);
	}

	public GameResponse executeAction(int id, ActionRequest actionRequest) {
		Game game = this.findGame(id);
		GameAction action = actionsMap.get(actionRequest.getAction());
		LOGGER.info("Executing Action {} on Game {}",action.getClass().getSimpleName(),game.getId());
		action.execute(actionRequest, game);

		return this.saveAndGenerateResponse(game);
	}

	private GameResponse saveAndGenerateResponse(Game game) {
		gameRepository.save(game);
		LOGGER.debug("Game with id {} saved",game.getId());
		return gameResponseTransformer.transform(game);
	}

	private List<Game> findGames(String user) {
		return Optional.ofNullable(user)
				.map(this::allGamesByUser)
				.orElse(gameRepository.findAll());
	}

	private List<Game> allGamesByUser(String user) {
		List<Game> allByUser = gameRepository.findAllByUser(user);

		if(allByUser.isEmpty())
			throw new NotFoundException("Games with user " + user + " not found");

		return allByUser;
	}

	private Game findGame(int id) {
		return gameRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Game with Id: " + id + " not found"));
	}
}
