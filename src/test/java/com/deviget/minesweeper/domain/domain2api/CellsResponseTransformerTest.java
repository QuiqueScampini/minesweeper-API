package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.api.response.RowResponse;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.CellContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.deviget.minesweeper.domain.model.CellContent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CellsResponseTransformerTest {

	@InjectMocks
	private CellsResponseTransformer responseTransformer;

	@Mock
	private Cell cell_1, cell_2, cell_3, cell_4;

	@BeforeEach
	void setUp() {
		openMocks(this);
	}

	@Test
	void transform() {
		List<List<Cell>> board = this.mockBoard();
		setContent(cell_1, REVEALED, -1);
		setContent(cell_2, FLAG, null);
		setContent(cell_3, REVEALED, 1);
		setContent(cell_4, NONE, null);

		List<RowResponse> rowList = responseTransformer.transform(board);

		assertEquals(2,rowList.size());

		RowResponse row_1 = rowList.get(0);
		assertCellResponse(row_1.getCols().get(0),REVEALED,-1);
		assertCellResponse(row_1.getCols().get(1),FLAG,null);

		RowResponse row_2 = rowList.get(1);
		assertCellResponse(row_2.getCols().get(0),REVEALED,1);
		assertCellResponse(row_2.getCols().get(1),NONE,null);
	}

	private void assertCellResponse(CellResponse cellResponse, CellContent content, Integer value) {
		assertEquals(content,cellResponse.getContent());
		assertEquals(value,cellResponse.getValue());
	}

	private void setContent(Cell cell, CellContent content, Integer value) {
		when(cell.getContent()).thenReturn(content);
		when(cell.getValue()).thenReturn(value);
	}

	private List<List<Cell>> mockBoard() {
		List<List<Cell>> board = new ArrayList<>();
		board.add(Arrays.asList(cell_1,cell_2));
		board.add(Arrays.asList(cell_3,cell_4));
		return board;
	}
}