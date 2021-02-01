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

	private int rows;

	private int cols;

	@Lob
	private Cell[][] board;

	private int leftFlags;

	private LocalDateTime resumedDate;

	private int gameTime;


	private Game() {
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
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
		private int rows;
		private int cols;
		private Cell[][] board;
		private int leftFlags;

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}

		public Builder withRows(int rows) {
			this.rows = rows;
			return this;
		}

		public Builder withCols(int cols) {
			this.cols = cols;
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
			game.rows = this.rows;
			game.cols = this.cols;
			game.board = this.board;
			game.leftFlags = leftFlags;
			return game;
		}
	}
}
