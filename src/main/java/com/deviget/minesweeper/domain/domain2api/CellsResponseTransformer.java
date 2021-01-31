package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.model.Cell;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CellsResponseTransformer {

	public List<CellResponse> transform(List<Cell> affectedCells) {
		return affectedCells.stream()
				.map(this::transform)
				.collect(Collectors.toList());
	}

	public CellResponse transform(Cell cell) {
		Integer value = cell.isHidden()? -1 : cell.getValue();
		return new CellResponse.Builder()
				.withRow(cell.getRow())
				.withCol(cell.getCol())
				.withCellContent(cell.getContent())
				.withValue(value)
				.build();
	}
}
