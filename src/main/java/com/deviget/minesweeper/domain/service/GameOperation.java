package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.domain.model.GameStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static com.deviget.minesweeper.domain.model.GameStatus.*;

@Component
public class GameOperation {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameOperation.class);

	private static final List<GameStatus> FINISHED_STATUS = Arrays.asList(WON, LOST);
	private static final List<GameStatus> PAUSED_STATUS = Arrays.asList(CREATED, PAUSED);

	public boolean isFinished(Game game) {
		return FINISHED_STATUS.contains(game.getStatus());
	}

	public void startIfPaused(Game game) {
		if(PAUSED_STATUS.contains(game.getStatus())){
			LOGGER.info("Game with id: {} update to IN_PROGRESS",game.getId() );
			game.setStatus(IN_PROGRESS);
			game.setLastUpdateTime(LocalDateTime.now());
		}
	}

	public void pauseGame(Game game) {
		if(IN_PROGRESS.equals(game.getStatus())) {
			int gameTime = this.calculateGameTime(game);
			game.setGameTime(gameTime);
			LOGGER.info("Game with id: {} new gameTime {}",game.getId(), gameTime);
		}
		game.setStatus(PAUSED);
	}

	public int calculateGameTime(Game game){
		int gameTime = game.getGameTime();
		if(IN_PROGRESS.equals(game.getStatus())) {
			long timeSinceUpdate = game.getLastUpdateTime().until(LocalDateTime.now(), ChronoUnit.SECONDS);
			return Math.toIntExact(timeSinceUpdate) + gameTime;
		}
		return gameTime;
	}
}
