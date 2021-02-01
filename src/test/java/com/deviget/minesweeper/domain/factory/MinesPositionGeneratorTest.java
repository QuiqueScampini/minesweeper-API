package com.deviget.minesweeper.domain.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.util.Pair;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class MinesPositionGeneratorTest {

	private static final int COLS = 3;
	private static final int ROWS = 3;
	private static final int BOARD_SIZE = COLS * ROWS;
	private static final int MINES = 3;

	@InjectMocks
	private MinesPositionGenerator positionGenerator;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Random randomGenerator;

	@BeforeEach
	void setUp() {
		openMocks(this);
		ReflectionTestUtils.setField(positionGenerator,"randomGenerator",randomGenerator);
		when(this.randomGenerator
				.ints(0, BOARD_SIZE)
				.distinct()
				.limit(MINES)
		).thenReturn(IntStream.of(4, 5, 8));
	}

	@Test
	void generate() {
		List<Pair<Integer, Integer>> minesPositions = positionGenerator.generate(ROWS, COLS, MINES);

		assertEquals(3,minesPositions.size());

		assertMine(minesPositions.get(0),1,1);
		assertMine(minesPositions.get(1),1,2);
		assertMine(minesPositions.get(2),2,2);
	}

	private void assertMine(Pair<Integer,Integer> mine, Integer row, Integer col){
		assertEquals(row,mine.getFirst());
		assertEquals(col,mine.getSecond());
	}
}