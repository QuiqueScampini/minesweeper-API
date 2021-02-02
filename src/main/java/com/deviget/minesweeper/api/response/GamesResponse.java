package com.deviget.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamesResponse {

	private final List<GameResponse> games;
	private final String user;

	public GamesResponse(List<GameResponse> games, String user) {
		this.games = games;
		this.user = user;
	}

	public List<GameResponse> getGames() {
		return games;
	}

	public String getUser() {
		return user;
	}
}
