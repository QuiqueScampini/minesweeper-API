package com.deviget.minesweeper.api.response;

import com.deviget.minesweeper.model.CellContent;

import static com.deviget.minesweeper.model.CellContent.REVEALED;

public class CellResponse {

	private Integer value;
	private final CellContent content;

	public CellResponse(CellContent content) {
		this.content = content;
	}

	public CellResponse(Integer value) {
		this.content = REVEALED;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public CellContent getContent() {
		return content;
	}

}
