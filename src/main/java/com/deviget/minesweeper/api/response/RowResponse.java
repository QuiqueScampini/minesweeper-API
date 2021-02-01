package com.deviget.minesweeper.api.response;

import java.util.List;

public class RowResponse {

	private final List<CellResponse> cells;

	public RowResponse(List<CellResponse> cells) {
		this.cells = cells;
	}

	public List<CellResponse> getCols() {
		return cells;
	}
}
