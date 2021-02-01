package com.deviget.minesweeper.api.response;

import com.deviget.minesweeper.model.GameStatus;

import java.util.List;

public class GameResponse {

	private	Integer id;
	private String user;
	private GameStatus status;
	private List<RowResponse> board;
	private int gameTime;
	private int leftFlags;

	private GameResponse() {
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public GameStatus getStatus() {
		return status;
	}

	public List<RowResponse> getBoard() {
		return board;
	}

	public int getLeftFlags() {
		return leftFlags;
	}

	public int getGameTime() {
		return gameTime;
	}

	public static class Builder {

		private	Integer id;
		private String user;
		private GameStatus status;
		private List<RowResponse> board;
		private int gameTime;
		private int leftFlags;

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}

		public Builder withStatus(GameStatus status) {
			this.status = status;
			return this;
		}

		public Builder withBoard(List<RowResponse> board) {
			this.board = board;
			return this;
		}

		public Builder withLeftFlags(int leftFlags) {
			this.leftFlags = leftFlags;
			return this;
		}

		public Builder withGameTime(int gameTime) {
			this.gameTime = gameTime;
			return this;
		}

		public GameResponse build() {
			GameResponse gameResponse = new GameResponse();
			gameResponse.id = this.id;
			gameResponse.user = this.user;
			gameResponse.status = this.status;
			gameResponse.board = this.board;
			gameResponse.leftFlags = this.leftFlags;
			gameResponse.gameTime = this.gameTime;
			return gameResponse;
		}
	}
}
