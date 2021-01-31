package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BoardFactoryTest {

	private static final int COLS = 3;
	private static final int ROWS = 3;
	private static final int BOARD_SIZE = COLS * ROWS;
	private static final int MINES = 3;

	@InjectMocks
	private BoardFactory factory;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Random randomGenerator;

	@Mock
	private GameRequest request;

	@BeforeEach
	public void setup(){
		openMocks(this);
		ReflectionTestUtils.setField(factory,"randomGenerator",randomGenerator);
		when(this.randomGenerator
				.ints(0, BOARD_SIZE)
				.distinct()
				.limit(MINES)
		).thenReturn(IntStream.of(4, 5, 8));
	}

	@Test
	public void create() {
		//Arrange
		when(request.getRows()).thenReturn(ROWS);
		when(request.getCols()).thenReturn(COLS);
		when(request.getMines()).thenReturn(MINES);

		//Act
		Cell[][] cells = factory.create(request);

		//Board assertion
		assertEquals(ROWS,cells.length);
		assertEquals(ROWS,cells[0].length);

		//Row 0 assertion
		assertEquals(1, cells[0][0].getValue());
		assertEquals(2, cells[0][1].getValue());
		assertEquals(2, cells[0][2].getValue());

		//Row 1 assertion
		assertEquals(1, cells[1][0].getValue());
		assertTrue(cells[1][1].isMine());
		assertTrue(cells[1][2].isMine());

		//Row 2 assertion
		assertEquals(1, cells[2][0].getValue());
		assertEquals(3, cells[2][1].getValue());
		assertTrue(cells[2][2].isMine());
	}
}