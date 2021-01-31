package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.ErrorResponse;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.controller.validator.RequestValidator;
import com.deviget.minesweeper.service.GameService;
import io.swagger.annotations.ApiResponse;
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
	@PostMapping("/createGame")
	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
	public GameResponse createGame(@RequestBody GameRequest gameRequest) {
		requestValidator.validateRequest(gameRequest);
		return gameService.createGame(gameRequest);
	}
}
