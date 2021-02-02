package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.controller.validator.RequestValidator;
import com.deviget.minesweeper.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minesweeper")
public class MinesWeeperController {

	@Autowired
	private RequestValidator requestValidator;

	@Autowired
	private GameService gameService;

	@GetMapping("/health-check")
	public String index() {
		return "I'm MinesWeeper Api and I'm OK";
	}

	@ResponseBody
	@PostMapping("/game")
	public GameResponse createGame(@RequestBody GameRequest gameRequest) {
		requestValidator.validateRequest(gameRequest);
		return gameService.createGame(gameRequest);
	}

	@ResponseBody
	@GetMapping("/game/{id}")
	public GameResponse retrieveGame(@PathVariable(value = "id") int id){
		return this. gameService.retrieveGame(id);
	}

	@ResponseBody
	@PatchMapping("/game/{id}")
	public GameResponse pauseGame(@PathVariable(value = "id") int id) {
		return this.gameService.pauseGame(id);
	}

	@ResponseBody
	@PutMapping("/game/{id}")
	public GameResponse executeAction(@RequestBody ActionRequest actionRequest, @PathVariable(value = "id") int id) {
		requestValidator.validateRequest(actionRequest);
		return this.gameService.executeAction(id,actionRequest);
	}
}
