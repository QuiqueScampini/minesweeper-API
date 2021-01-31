package com.deviget.minesweeper.api.response;

import java.util.List;

public class GameResponse {

	private	Integer id;
	private String user;
	private int gameTime;
	private int leftFlags;
	private List<CellResponse> affectedCells;

	public GameResponse(Integer id, String user, int gameTime, int leftFlags,List<CellResponse> affectedCells) {
		this.id = id;
		this.user = user;
		this.affectedCells = affectedCells;
		this.gameTime = gameTime;
		this.leftFlags = leftFlags;
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public int getGameTime() {
		return gameTime;
	}

	public int getLeftFlags() {
		return leftFlags;
	}

	public List<CellResponse> getAffectedCells() {
		return affectedCells;
	}
}
