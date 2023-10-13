package model.interfaces;

import model.Board;
import utils.enums.GameSymbol;

public interface BoardUtils {
	Board copyBoard();

	boolean hasEmptyCells();

	void printBoard();

	GameSymbol getBoardCell(int row, int col);
}
