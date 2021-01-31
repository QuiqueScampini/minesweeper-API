package com.deviget.minesweeper.api.response;

import com.deviget.minesweeper.model.CellContent;

public class CellResponse {

	int row;
	int col;
	private final boolean hidden;
	private long value;
	private CellContent content;

	public CellResponse(int row, int col,CellContent content) {
		this.row = row;
		this.col = col;
		this.hidden = true;
		this.content = content;
	}

	public CellResponse(int row, int col, long value) {
		this.row = row;
		this.col = col;
		this.hidden = false;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isHidden() {
		return hidden;
	}

	public long getValue() {
		return value;
	}

	public CellContent getContent() {
		return content;
	}
}
