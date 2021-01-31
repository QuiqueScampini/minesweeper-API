package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;

import java.util.List;

public interface GameAction {

	List<Cell> execute(ActionRequest request, Game game);
}
