package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.Action;
import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.domain.action.FlagAction;
import com.deviget.minesweeper.domain.action.GameAction;
import com.deviget.minesweeper.domain.domain2api.GameResponseTransformer;
import com.deviget.minesweeper.domain.factory.GameFactory;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static com.deviget.minesweeper.api.request.Action.FLAG;

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
	public GameService(FlagAction flagAction) {
		actionsMap.put(FLAG,flagAction);
	}

	public GameResponse createGame(GameRequest gameRequest) {
		Game game = gameFactory.create(gameRequest);
		return this.saveAndGenerateResponse(game, Collections.emptyList());
	}

	public GameResponse executeAction(int id, ActionRequest actionRequest) {
		Game game = this.findGame(id);
		GameAction action = actionsMap.get(actionRequest.getAction());
		List<Cell> affectedCells = action.execute(actionRequest, game);

		return this.saveAndGenerateResponse(game,affectedCells);
	}

	private GameResponse saveAndGenerateResponse(Game game,List<Cell> affectedCells) {
		gameRepository.save(game);
		return gameResponseTransformer.transform(game,affectedCells);
	}

	private Game findGame(int id) {
		//TODO Map to 404
		return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
	}

}
