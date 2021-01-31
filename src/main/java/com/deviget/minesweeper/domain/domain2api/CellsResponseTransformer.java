package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.model.Cell;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CellsResponseTransformer {

	public List<CellResponse> transform(Cell[][] board) {
		int rows = board.length;
		int cols = board[0].length;

		List<CellResponse> response = new ArrayList<>();
		for(int row = 0; row< rows; row++){
			for(int col = 0; col< cols; col++){
				Cell cell = board[row][col];
				response.add(this.transform(cell,row,col));
			}
		}
		return response;
	}

	public CellResponse transform(Cell cell,int row, int col) {
		if(cell.isHidden()){
			return new CellResponse(row,col, cell.getContent());
		}
		return new CellResponse(row,col,cell.getValue());
	}
}
