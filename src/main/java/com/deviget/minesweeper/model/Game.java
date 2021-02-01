package com.deviget.minesweeper.model;

import javax.persistence.*;
import java.util.List;

import static com.deviget.minesweeper.model.GameStatus.CREATED;

@Entity
@Table(indexes = @Index(columnList = "user"))
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private final String user;

	private GameStatus status;

	@Lob
	private final List<List<Cell>> board;

	private int leftFlags;

	public Game(String user, List<List<Cell>> board, int leftFlags) {
		this.user = user;
		this.status = CREATED;
		this.board = board;
		this.leftFlags = leftFlags;
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GameStatus getStatus() {
		return status;
	}

	public List<List<Cell>> getBoard() {
		return board;
	}

	public void setLeftFlags(int leftFlags) {
		this.leftFlags = leftFlags;
	}

	public int getLeftFlags() {
		return leftFlags;
	}
}
