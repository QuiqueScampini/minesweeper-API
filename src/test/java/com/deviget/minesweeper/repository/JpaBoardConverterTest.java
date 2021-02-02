package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.deviget.minesweeper.model.CellContent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JpaBoardConverterTest {

	private JpaBoardConverter converter;

	@BeforeEach
	void setUp() {
		converter = new JpaBoardConverter();
	}

	@Test
	void convert_reconvert() {
		List<List<Cell>> board =  new ArrayList<>();
		List<Cell> row_1 = new ArrayList<>();
		row_1.add(new Cell(0,0,3, NONE));
		row_1.add(new Cell(0,1,0, QUESTION));
		board.add(row_1);

		List<Cell> row_2 = new ArrayList<>();
		row_2.add(new Cell(1,0,-1, REVEALED));
		row_2.add(new Cell(1,0,2, FLAG));
		board.add(row_2);

		byte[] s = converter.convertToDatabaseColumn(board);

		assertNotNull(s);

		List<List<Cell>> reconstructedBoard = converter.convertToEntityAttribute(s);

		assertReconstructedCell(reconstructedBoard,0,0,3,NONE);
		assertReconstructedCell(reconstructedBoard,0,1,0,QUESTION);
		assertReconstructedCell(reconstructedBoard,1,0,-1,REVEALED);
		assertReconstructedCell(reconstructedBoard,1,1,2,FLAG);
	}

	private void assertReconstructedCell(List<List<Cell>> board, int row, int col, Integer value, CellContent content){
		Cell cell = board.get(row).get(col);
		assertNotNull(cell);
		assertEquals(value,cell.getValue());
		assertEquals(content,cell.getContent());
	}
}