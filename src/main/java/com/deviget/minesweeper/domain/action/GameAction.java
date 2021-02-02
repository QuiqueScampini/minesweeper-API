package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Game;

public interface GameAction {

	void execute(ActionRequest request, Game game);

}
