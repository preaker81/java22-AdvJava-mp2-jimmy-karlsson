package model;

import model.interfaces.BoardOperations;
import model.interfaces.BoardUtils;
import model.interfaces.WinConditionOperations;
import utils.enums.GameSymbol;

/**
 * The Board class represents a Tic-Tac-Toe board and implements multiple
 * interfaces for operations, win conditions, and utility functions.
 */
public class Board implements BoardOperations, WinConditionOperations, BoardUtils {
	// 3x3 board to store the game state.
	private GameSymbol[][] board = new GameSymbol[3][3];

	/**
	 * Default constructor. Initializes the board to EMPTY.
	 */
	public Board() {
		// Initialize the board with EMPTY symbols
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = GameSymbol.EMPTY;
			}
		}
	}

	/**
	 * Sets the cell at (row, col) to EMPTY.
	 */
	@Override
	public void setEmpty(int row, int col) {
		// Check for valid row and column indices
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			board[row][col] = GameSymbol.EMPTY;
		}
	}

	/**
	 * Checks if a move is valid at (row, col).
	 */
	@Override
	public boolean isValidMove(int row, int col) {
		// Validate indices and cell emptiness
		return (row >= 0 && row < 3 && col >= 0 && col < 3) && (board[row][col] == GameSymbol.EMPTY);
	}

	/**
	 * Places a mark on the board at (row, col).
	 */
	@Override
	public void placeMark(GameSymbol mark, int row, int col) {
		// Place the mark only if the move is valid
		if (isValidMove(row, col)) {
			board[row][col] = mark;
		}
	}

	/**
	 * Checks for a winner on the board.
	 */
	@Override
	public boolean isWinner() {
		return isXWinner() || isOWinner();
	}

	/**
	 * Checks for a draw on the board.
	 */
	@Override
	public boolean isDraw() {
		// Scan board for any EMPTY cells
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == GameSymbol.EMPTY) {
					return false;
				}
			}
		}
		// If no EMPTY cells are found and no winner, it's a draw
		return !isWinner();
	}

	/**
	 * Checks if X is a winner.
	 */
	@Override
	public boolean isXWinner() {
		return checkWinner(GameSymbol.X);
	}

	/**
	 * Checks if O is a winner.
	 */
	@Override
	public boolean isOWinner() {
		return checkWinner(GameSymbol.O);
	}

	/**
	 * Checks for a winner given a specific mark.
	 */
	private boolean checkWinner(GameSymbol mark) {
		// Check rows and columns for winner
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark)
				return true;
			if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)
				return true;
		}
		// Check diagonals for winner
		if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark)
			return true;
		if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark)
			return true;
		return false;
	}

	/**
	 * Creates a deep copy of the current board.
	 */
	@Override
	public Board copyBoard() {
		Board newBoard = new Board();
		// Copy each cell to the new board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newBoard.board[i][j] = this.board[i][j];
			}
		}
		return newBoard;
	}

	/**
	 * Checks if the board has any EMPTY cells.
	 */
	@Override
	public boolean hasEmptyCells() {
		// Scan the board for any EMPTY cells
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == GameSymbol.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Prints the current state of the board to the console.
	 */
	@Override
	public void printBoard() {
		// Iterate through each cell and print its value
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] == GameSymbol.EMPTY ? "-" : board[i][j]);
				if (j < 2)
					System.out.print("|");
			}
			System.out.println();
		}
	}

	/**
	 * Retrieves the value at a specific cell on the board.
	 */
	@Override
	public GameSymbol getBoardCell(int row, int col) {
		// Check for valid row and column indices
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			return board[row][col];
		} else {
			throw new IllegalArgumentException("Row and column indices must be between 0 and 2, inclusive.");
		}
	}

}
