package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.api.response.RowResponse;
import com.deviget.minesweeper.model.Cell;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.deviget.minesweeper.model.CellContent.REVEALED;

@Component
class CellsResponseTransformer {

	public List<RowResponse> transform(List<List<Cell>> board) {
		return board.stream()
				.map(this::transformRow)
				.collect(Collectors.toList());
	}

	private RowResponse transformRow(List<Cell> row) {
		List<CellResponse> collect = row.stream()
				.map(this::transformCell)
				.collect(Collectors.toList());
		return new RowResponse(collect);
	}

	private CellResponse transformCell(Cell cell) {
		if(REVEALED.equals(cell.getContent()))
			return new CellResponse(cell.getValue());

		return new CellResponse(cell.getContent());
	}
}
