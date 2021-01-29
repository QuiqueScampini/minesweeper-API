package com.deviget.minesweeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minesweeper")
public class MinesWeeperController {

	@GetMapping("/")
	public String index() {
		return "MinesWeeper Api";
	}

}
