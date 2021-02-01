package com.deviget.minesweeper.model;

import java.io.Serializable;

import static com.deviget.minesweeper.model.CellContent.NONE;

public class Cell implements Serializable {

	private final int row;
	private final int col;
	private boolean hidden;
	private CellContent content;
	private final Integer value;

	public Cell(int row, int col,Integer value) {
		this.row = row;
		this.col = col;
		this.value = value;
		hidden = true;
		content = NONE;
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

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public CellContent getContent() {
		return content;
	}

	public void setContent(CellContent content) {
		this.content = content;
	}

	public Integer getValue() {
		return value;
	}

}
