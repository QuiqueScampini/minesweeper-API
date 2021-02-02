package com.deviget.minesweeper.model;

import java.io.Serializable;

public class Cell implements Serializable {

	private int row;
	private int col;
	private CellContent content;
	private Integer value;

	public Cell() {
	}

	public Cell(int row, int col, Integer value, CellContent content) {
		this.row = row;
		this.col = col;
		this.value = value;
		this.content = content;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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
