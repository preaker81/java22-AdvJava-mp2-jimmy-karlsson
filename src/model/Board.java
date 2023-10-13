package model;

import model.interfaces.BoardBasicOperations;
import model.interfaces.BoardUtilityOperations;
import model.interfaces.BoardWinOperations;
import utils.enums.Mark;

public class Board implements BoardBasicOperations, BoardWinOperations, BoardUtilityOperations {
	private Mark[][] board = new Mark[3][3];

	public Board() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = Mark.EMPTY;
			}
		}
	}

	@Override
	public Mark getCurrentPlayer() {
		int xCount = 0;
		int oCount = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == Mark.X) {
					xCount++;
				} else if (board[i][j] == Mark.O) {
					oCount++;
				}
			}
		}

		return (xCount == oCount) ? Mark.X : Mark.O;
	}

	@Override
	public void setEmpty(int row, int col) {
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			board[row][col] = Mark.EMPTY;
		}
	}

	@Override
	public boolean isValidMove(int row, int col) {
		return (row >= 0 && row < 3 && col >= 0 && col < 3) && (board[row][col] == Mark.EMPTY);
	}

	@Override
	public void placeMark(Mark mark, int row, int col) {
		if (isValidMove(row, col)) {
			board[row][col] = mark;
		}
	}

	@Override
	public boolean isWinner() {
		return isXWinner() || isOWinner();
	}

	@Override
	public boolean isDraw() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == Mark.EMPTY) {
					return false;
				}
			}
		}
		return !isWinner();
	}

	@Override
	public boolean isXWinner() {
		return checkWinner(Mark.X);
	}

	@Override
	public boolean isOWinner() {
		return checkWinner(Mark.O);
	}

	private boolean checkWinner(Mark mark) {
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark)
				return true;
			if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)
				return true;
		}
		if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark)
			return true;
		if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark)
			return true;
		return false;
	}

	@Override
	public Board copyBoard() {
		Board newBoard = new Board();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newBoard.board[i][j] = this.board[i][j];
			}
		}
		return newBoard;
	}

	@Override
	public boolean hasEmptyCells() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == Mark.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] == Mark.EMPTY ? "-" : board[i][j]);
				if (j < 2)
					System.out.print("|");
			}
			System.out.println();
		}
	}

	@Override
	public Mark getBoardCell(int row, int col) {
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			return board[row][col];
		} else {
			throw new IllegalArgumentException("Row and column indices must be between 0 and 2, inclusive.");
		}
	}

}
