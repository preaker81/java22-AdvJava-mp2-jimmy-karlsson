package ticTacToe.interfaces;

import ticTacToe.Board;
import ticTacToe.enums.Mark;

public interface BoardOperations {

	Mark getCurrentPlayer();

	void setEmpty(int row, int col);

	boolean isValidMove(int row, int col);

	void placeMark(String mark, int row, int col);

	boolean isWinner();

	boolean isDraw();

	boolean isXWinner();

	boolean isOWinner();

	Board copyBoard();

	boolean hasEmptyCells();

	void printBoard();

	void setPreviousCell();

	void setNexBoard();

	Board getChildBoards();

	void createPossibleChildBoards();

	void placeMark(Mark mark, int row, int col);
	
	public Mark getBoardCell(int row, int col);

}