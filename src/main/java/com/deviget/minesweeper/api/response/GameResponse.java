package com.deviget.minesweeper.api.response;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {

	private	Integer id;
	private String user;
	private int gameTime;
	private int leftFlags;
	private List<CellResponse> affectedCells;

	private GameResponse() {
	}

	public GameResponse(Integer id, String user, int gameTime, int leftFlags, List<CellResponse> affectedCells) {
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

	public static class Builder {

		private	Integer id;
		private String user;
		private int gameTime;
		private int leftFlags;
		private List<CellResponse> affectedCells = new ArrayList<>();

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}

		public Builder withGameTime(int gameTime) {
			this.gameTime = gameTime;
			return this;
		}

		public Builder withLeftFlags(int leftFlags) {
			this.leftFlags = leftFlags;
			return this;
		}

		public Builder withAffectedCells(List<CellResponse> affectedCells) {
			this.affectedCells = affectedCells;
			return this;
		}

		public GameResponse build() {
			GameResponse gameResponse = new GameResponse();
			gameResponse.id = this.id;
			gameResponse.user = this.user;
			gameResponse.gameTime = this.gameTime;
			gameResponse.leftFlags = this.leftFlags;
			gameResponse.affectedCells = this.affectedCells;
			return gameResponse;
		}
	}
}
