package com.deviget.minesweeper.domain.factory;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class MinesPositionGenerator {

	private final Random randomGenerator;

	public MinesPositionGenerator() {
		this.randomGenerator = new Random();
	}

	/**
	 * This method gets the random position of the mines
	 * 		1. We get the board size
	 * 	  	2. We get 3 random numbers between 0 and 8
	 * 	  	3. We calculate the (row,col) position in base of that numbers to create a list of Pairs
	 * Ej:
	 * 	We have 3 mines in a 3x3 board
	 * 		1. 3*3= 9
	 * 		2. For example (4,5,8)
	 * 		3. 4 -> (1,1) , 5 -> (1,2) and 8 -> (2,2) being 1 -> (0,0).
	 *
	 * **/
	public List<Pair<Integer, Integer>> generate(int rows, int cols, int mines){
		int boardSize = cols * rows;
		return this.randomGenerator.ints(0, boardSize)
				.distinct()
				.limit(mines)
				.mapToObj(positionNumber -> Pair.of(positionNumber / rows, positionNumber % cols))
				.collect(Collectors.toList());
	}
}
