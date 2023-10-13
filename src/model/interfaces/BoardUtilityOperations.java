package model.interfaces;

import model.Board;
import utils.enums.Mark;

public interface BoardUtilityOperations {
	Board copyBoard();

	boolean hasEmptyCells();

	void printBoard();

	Mark getBoardCell(int row, int col);
}
