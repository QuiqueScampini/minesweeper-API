package com.deviget.minesweeper.api.response;

import com.deviget.minesweeper.model.CellContent;

public class CellResponse {

	int row;
	int col;
	private Integer value;
	private CellContent content;

	private CellResponse(){

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Integer getValue() {
		return value;
	}

	public CellContent getContent() {
		return content;
	}

	public static class Builder {

		int row;
		int col;
		private Integer value;
		private CellContent content;

		public Builder withRow(int row){
			this.row = row;
			return this;
		}

		public Builder withCol(int col){
			this.col = col;
			return this;
		}

		public Builder withValue(Integer value){
			this.value = value;
			return this;
		}

		public Builder withCellContent(CellContent content){
			this.content = content;
			return this;
		}

		public CellResponse build() {
			CellResponse cellResponse = new CellResponse();
			cellResponse.row = this.row;
			cellResponse.col = this.col;
			cellResponse.value = this.value;
			cellResponse.content = this.content;
			return cellResponse;
		}
	}
}
