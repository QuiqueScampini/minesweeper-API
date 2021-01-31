package com.deviget.minesweeper.model;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "user"))
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String user;

	public Game(String user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public String getUser() {
		return user;
	}
}
