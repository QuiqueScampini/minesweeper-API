package com.deviget.minesweeper.api.response;

public class GameResponse {

	private	Integer id;
	private String user;

	public GameResponse(Integer id, String user) {
		this.id = id;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public String getUser() {
		return user;
	}
}
