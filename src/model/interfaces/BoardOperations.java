package model.interfaces;

import utils.enums.GameSymbol;

public interface BoardOperations {
	void setEmpty(int row, int col);

	boolean isValidMove(int row, int col);

	void placeMark(GameSymbol mark, int row, int col);
}
