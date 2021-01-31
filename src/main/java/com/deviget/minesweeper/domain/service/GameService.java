package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.domain.domain2api.GameResponseTransformer;
import com.deviget.minesweeper.domain.factory.GameFactory;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.deviget.minesweeper.api.request.Action.FLAG;

@Service
public class GameService {

	@Autowired
	private GameResponseTransformer gameResponseTransformer;

	@Autowired
	private GameFactory gameFactory;

	@Autowired
	private GameRepository gameRepository;

	public GameResponse createGame(GameRequest gameRequest) {
		Game game = gameFactory.create(gameRequest);
		return gameResponseTransformer.transform(gameRepository.save(game));
	}

	public GameResponse executeAction(int id, ActionRequest actionRequest) {
		Game game = this.findGame(id);
		if(FLAG.equals(actionRequest.getAction())) {
			List<Cell> affectedCells = this.flag(game,actionRequest);
			return gameResponseTransformer.transform(gameRepository.save(game),affectedCells.get(0),actionRequest.getRow(),actionRequest.getCol());
		}
		return this.reveal(game,actionRequest);
	}

	private GameResponse reveal(Game game, ActionRequest actionRequest) {
		return null;
	}

	private List<Cell> flag(Game game, ActionRequest actionRequest) {
		Cell cell = this.retrieveCell(game, actionRequest);

		if(!cell.isHidden())
			return Collections.emptyList();

		switch (cell.getContent()) {
			case NONE:
				cell.setContent(CellContent.FLAG);
				game.setLeftFlags(game.getLeftFlags() - 1 );
				break;
			case FLAG:
				cell.setContent(CellContent.QUESTION);
				game.setLeftFlags(game.getLeftFlags() + 1 );
				break;
			case QUESTION:
				cell.setContent(CellContent.NONE);
				break;
		}
		gameRepository.save(game);
		return Collections.singletonList(cell);
	}

	private Cell retrieveCell(Game game, ActionRequest actionRequest) {
		int row = actionRequest.getRow();
		int col = actionRequest.getCol();
		//TODO validate it exists
		return game.getBoard()[row][col];
	}

	private Game findGame(int id) {
		//TODO Map to 404
		return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
	}

}
