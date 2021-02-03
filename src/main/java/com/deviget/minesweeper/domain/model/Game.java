package com.deviget.minesweeper.domain.model;

import com.deviget.minesweeper.repository.JpaBoardConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.deviget.minesweeper.domain.model.GameStatus.CREATED;

@Entity
@Table(indexes = @Index(columnList = "user"))
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String user;

	private GameStatus status;

	@Convert(converter = JpaBoardConverter.class)
	@Column(name = "board", columnDefinition = "json")
	private List<List<Cell>> board;

	private int leftFlags;

	private LocalDateTime lastUpdateTime;

	private int gameTime;

	public Game() {

	}

	public Game(String user, List<List<Cell>> board, int leftFlags) {
		this.user = user;
		this.status = CREATED;
		this.board = board;
		this.leftFlags = leftFlags;
		this.gameTime = 0;
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

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}
}
