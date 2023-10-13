package model;

import java.util.ArrayList;
import java.util.List;
import utils.enums.GameSymbol;

/**
 * This class represents the artificial intelligence for the TicTacToe game. It
 * uses the MinMax algorithm to evaluate board states and make the best possible
 * move.
 */
public class TicTacToeAI {

	/**
	 * Utilizes the MinMax algorithm to find the best move for the current player.
	 * 
	 * @param board         The current game board.
	 * @param currentPlayer The current player's symbol.
	 * @return The best move for the current player.
	 */
	public Move minMax(Board board, GameSymbol currentPlayer) {
		// Check if X is a winner and return a move with score 1
		if (board.isXWinner()) {
			return new Move(1);
		}
		// Check if O is a winner and return a move with score -1
		if (board.isOWinner()) {
			return new Move(-1);
		}
		// Check if the board is full and return a move with score 0
		if (!board.hasEmptyCells()) {
			return new Move(0);
		}

		// Evaluate all possible moves and their outcomes
		List<Move> moves = evaluateMoves(board, currentPlayer);

		// Find the index of the best move
		int bestMoveIndex = currentPlayer == GameSymbol.X ? findBestMoveForMax(moves) : findBestMoveForMin(moves);

		return moves.get(bestMoveIndex);
	}

	/**
	 * Evaluate all possible moves for the current player.
	 * 
	 * @param board         The current game board.
	 * @param currentPlayer The current player's symbol.
	 * @return A list of moves with associated scores.
	 */
	private List<Move> evaluateMoves(Board board, GameSymbol currentPlayer) {
		List<Move> moves = new ArrayList<>();

		// Loop through each cell in the 3x3 grid
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				// Check if the cell is empty
				if (board.isValidMove(row, col)) {
					// Evaluate the move and add to list
					moves.add(evaluateMove(board, row, col, currentPlayer));
				}
			}
		}

		return moves;
	}

	/**
	 * Evaluate an individual move for the current player.
	 * 
	 * @param board         The current game board.
	 * @param row           The row number for the cell.
	 * @param col           The column number for the cell.
	 * @param currentPlayer The current player's symbol.
	 * @return The move with its associated score.
	 */
	private Move evaluateMove(Board board, int row, int col, GameSymbol currentPlayer) {
		Move move = new Move(row, col); // Create a new move
		Board boardCopy = board.copyBoard(); // Copy the board to simulate the move

		// Simulate the move
		boardCopy.placeMark(currentPlayer, row, col);
		// Calculate the score of the move
		move.setScore(minMax(boardCopy, switchPlayer(currentPlayer)).getScore());

		// Reset the board cell back to empty
		board.setEmpty(row, col);
		return move;
	}

	/**
	 * Utility to switch the player from X to O or O to X.
	 * 
	 * @param currentPlayer The current player's symbol.
	 * @return The opposite player's symbol.
	 */
	private GameSymbol switchPlayer(GameSymbol currentPlayer) {
		return (currentPlayer == GameSymbol.X) ? GameSymbol.O : GameSymbol.X;
	}

	/**
	 * Find the best move for the maximizing player.
	 * 
	 * @param moves List of moves to evaluate.
	 * @return The index of the best move.
	 */
	private int findBestMoveForMax(List<Move> moves) {
		return findBestMove(moves, Integer.MIN_VALUE, true);
	}

	/**
	 * Find the best move for the minimizing player.
	 * 
	 * @param moves List of moves to evaluate.
	 * @return The index of the best move.
	 */
	private int findBestMoveForMin(List<Move> moves) {
		return findBestMove(moves, Integer.MAX_VALUE, false);
	}

	/**
	 * General utility to find the best move.
	 * 
	 * @param moves            List of moves to evaluate.
	 * @param initialBestScore The initial best score to compare against.
	 * @param isMax            A boolean flag indicating if we are maximizing or
	 *                         minimizing.
	 * @return The index of the best move.
	 */
	private int findBestMove(List<Move> moves, int initialBestScore, boolean isMax) {
		int bestMove = -1; // Initialize to an invalid index
		int bestScore = initialBestScore;

		// Loop through the moves to find the best one
		for (int i = 0; i < moves.size(); i++) {
			int currentScore = moves.get(i).getScore();

			if (isMax ? currentScore > bestScore : currentScore < bestScore) {
				bestScore = currentScore;
				bestMove = i;
			}
		}

		return bestMove;
	}

	// Inner class to represent a move in the TicTacToe game
	public class Move {
		private int row;
		private int col;
		private int score;

		public Move(int score) {
			this.score = score;
		}

		public Move(int row, int col) {
			this.row = row;
			this.col = col;
		}

		// Getter and Setter methods for the properties
		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}
	}
}
