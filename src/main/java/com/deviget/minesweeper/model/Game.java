package com.deviget.minesweeper.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(columnList = "user"))
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String user;

	@Lob
	private Cell[][] board;

	private LocalDateTime resumedDate;

	private int gameTime;

	private int leftFlags;

	private Game() {
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public Cell[][] getBoard() {
		return board;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setLeftFlags(int leftFlags) {
		this.leftFlags = leftFlags;
	}

	public int getLeftFlags() {
		return leftFlags;
	}

	public static class Builder {

		private String user;
		private Cell[][] board;
		private int leftFlags;

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}

		public Builder withBoard(Cell[][] board) {
			this.board = board;
			return this;
		}

		public Builder withLeftFlags(int leftFlags) {
			this.leftFlags = leftFlags;
			return this;
		}

		public Game build(){
			Game game = new Game();
			game.user = this.user;
			game.board = this.board;
			game.leftFlags = leftFlags;
			return game;
		}
	}
}
