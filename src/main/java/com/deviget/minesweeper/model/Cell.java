package com.deviget.minesweeper.model;

import java.io.Serializable;

import static com.deviget.minesweeper.model.CellContent.NONE;

public class Cell implements Serializable {

	private  int row;
	private int col;
	private boolean mine;
	private boolean hidden;
	private CellContent content;
	private Integer value;

	public Cell(int row, int col, boolean mine, Integer value) {
		this.mine = mine;
		this.value = value;
		hidden = true;
		content = NONE;
	}

	public void setContent(CellContent content) {
		this.content = content;
	}

	public boolean isMine() {
		return mine;
	}

	public boolean isHidden() {
		return hidden;
	}

	public CellContent getContent() {
		return content;
	}

	public Integer getValue() {
		return value;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
