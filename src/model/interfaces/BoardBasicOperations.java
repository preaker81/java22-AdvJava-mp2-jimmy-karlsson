package model.interfaces;

import utils.enums.Mark;

public interface BoardBasicOperations {
	Mark getCurrentPlayer();

	void setEmpty(int row, int col);

	boolean isValidMove(int row, int col);

	void placeMark(Mark mark, int row, int col);
}
