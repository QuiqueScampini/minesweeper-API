package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BoardFactoryTest {

	private static final int COLS = 3;
	private static final int ROWS = 3;
	private static final int MINES = 3;

	@InjectMocks
	private BoardFactory factory;

	@Mock
	private MinesPositionGenerator minesPositionsGenerator;

	@BeforeEach
	public void setup(){
		openMocks(this);
	}

	@Test
	public void create() {
		//Arrange
		List<Pair<Integer, Integer>> pairs = Arrays.asList(Pair.of(1,1),Pair.of(1,2),Pair.of(2,2));
		when(minesPositionsGenerator.generate(ROWS,COLS,MINES)).thenReturn(pairs);

		//Act
		List<List<Cell>> board = factory.create(COLS,COLS,MINES);

		//Board assertion
		assertEquals(ROWS,board.size());
		assertTrue(board.stream().allMatch( row -> COLS == row.size()));

		//Row 0 assertion
		List<Cell> row_0 = board.get(0);
		assertCellValue(1,row_0.get(0));
		assertCellValue(2, row_0.get(1));
		assertCellValue(2, row_0.get(2));

		//Row 1 assertion
		List<Cell> row_1 = board.get(1);
		assertCellValue(1, row_1.get(0));
		assertCellValue(-1, row_1.get(1));
		assertCellValue(-1, row_1.get(2));

		//Row 2 assertion
		List<Cell> row_2 = board.get(2);
		assertCellValue(1, row_2.get(0));
		assertCellValue(3, row_2.get(1));
		assertCellValue(-1, row_2.get(2));
	}

	private void assertCellValue(Integer value, Cell cell) {
		assertEquals(value, cell.getValue());
	}
}