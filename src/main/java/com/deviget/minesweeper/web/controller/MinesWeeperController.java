package com.deviget.minesweeper.web.controller;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.ErrorResponse;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.web.validator.RequestValidator;
import com.deviget.minesweeper.domain.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

	@RequestMapping(value = {"","/api-docs"})
	public RedirectView redirect() {
		return new RedirectView("/swagger-ui.html");
	}

	@ResponseBody
	@GetMapping("/game")
	@ApiOperation(value = "Retrieve games", response = GameResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = GameResponse.class, message = "Games Retrieved"),
			@ApiResponse(code = 404, response = ErrorResponse.class, message = "Games of user not Found"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
	})
	public GamesResponse retrieveGames(@RequestParam(value = "user", required = false) final String user){
		return this. gameService.retrieveGames(user);
	}

	@ResponseBody
	@PostMapping("/game")
	@ApiOperation(value = "Create a new game", response = GameResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = GameResponse.class, message = "Game Created"),
			@ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
	})
	public GameResponse createGame(@RequestBody GameRequest gameRequest) {
		requestValidator.validateRequest(gameRequest);
		return gameService.createGame(gameRequest);
	}

	@ResponseBody
	@GetMapping("/game/{id}")
	@ApiOperation(value = "Retrieve existing game", response = GameResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = GameResponse.class, message = "Game Retrieved"),
			@ApiResponse(code = 404, response = ErrorResponse.class, message = "Game not Found"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
	})
	public GameResponse retrieveGame(@PathVariable(value = "id") int id){
		return this. gameService.retrieveGame(id);
	}

	@ResponseBody
	@PatchMapping("/game/{id}")
	@ApiOperation(value = "Pause existing game", response = GameResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = GameResponse.class, message = "Game Paused"),
			@ApiResponse(code = 404, response = ErrorResponse.class, message = "Game not Found"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
	})
	public GameResponse pauseGame(@PathVariable(value = "id") int id) {
		return this.gameService.pauseGame(id);
	}

	@ResponseBody
	@PutMapping("/game/{id}")
	@ApiOperation(value = "Execute Game Action", response = GameResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = GameResponse.class, message = "Action Executed"),
			@ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
			@ApiResponse(code = 404, response = ErrorResponse.class, message = "Game not Found"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
	})
	public GameResponse executeAction(@RequestBody ActionRequest actionRequest,
									  @PathVariable(value = "id") int id) {
		return this.gameService.executeAction(id,actionRequest);
	}
}
