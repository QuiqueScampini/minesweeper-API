package com.deviget.minesweeper.model;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "user"))
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String user;

	@Lob
	private Cell[][] board;

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

	public static class Builder {

		private String user;
		private Cell[][] board;

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}

		public Builder withBoard(Cell[][] board) {
			this.board = board;
			return this;
		}

		public Game build(){
			Game game = new Game();
			game.user = this.user;
			game.board = this.board;
			return game;
		}
	}
}
