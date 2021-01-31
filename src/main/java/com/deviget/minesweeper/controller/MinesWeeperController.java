package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minesweeper")
public class MinesWeeperController {

	@Autowired
	private GameService gameService;

	@GetMapping("/health-check")
	public String index() {
		return "I'm MinesWeeper Api and I'm OK";
	}

	@ResponseBody
	@PostMapping("/createGame")
	public String createGame(@RequestBody GameRequest gameRequest, @RequestParam String user) {
		return gameService.createGame(gameRequest,user);
	}

}
