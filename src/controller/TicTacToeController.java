package controller;

import model.Board;
import model.TicTacToeAI;
import model.TicTacToeAI.Move;
import utils.enums.GameSymbol;

/**
 * TicTacToeController class serves as the controller for the Tic-Tac-Toe game.
 * This class controls the flow of the game, validates moves, and switches
 * players.
 */
public class TicTacToeController {
	private Board board; // The game board
	private TicTacToeAI ai; // The AI component for providing hints or AI moves
	private GameSymbol currentPlayer; // Represents the current player's symbol ('X' or 'O')

	/**
	 * Constructor initializes a new game board and AI instance. Sets the starting
	 * player as 'X'.
	 */
	public TicTacToeController() {
		this.board = new Board();
		this.ai = new TicTacToeAI();
		this.currentPlayer = GameSymbol.X;
	}

	/**
	 * Provides a hint for the current player by invoking the minMax algorithm from
	 * TicTacToeAI.
	 * 
	 * @return Move suggested by the AI.
	 */
	public Move provideHint() {
		return ai.minMax(board, currentPlayer);
	}

	/**
	 * Checks if a move is valid on the current game board.
	 * 
	 * @param row Row index for the move.
	 * @param col Column index for the move.
	 * @return true if the move is valid, false otherwise.
	 */
	public boolean isValidMove(int row, int col) {
		return board.isValidMove(row, col);
	}

	/**
	 * Places the mark of the current player on the board if the move is valid. Then
	 * switches to the other player.
	 * 
	 * @param row Row index for the move.
	 * @param col Column index for the move.
	 */
	public void makeMove(int row, int col) {
		if (isValidMove(row, col)) {
			board.placeMark(currentPlayer, row, col);
			switchPlayer();

			// Print the best move for the next player
			Move bestMove = provideHint();
			System.out.println("Best move for " + currentPlayer + ": (" + bestMove.row + ", " + bestMove.col + ")");
		}
	}

	/**
	 * Switches the current player from 'X' to 'O' or vice versa.
	 */
	public void switchPlayer() {
		currentPlayer = (currentPlayer == GameSymbol.X) ? GameSymbol.O : GameSymbol.X;
	}

	/**
	 * Checks if the current board state represents a win.
	 * 
	 * @return true if there's a winner, false otherwise.
	 */
	public boolean isWinner() {
		return board.isWinner();
	}

	/**
	 * Checks if the current board state represents a draw.
	 * 
	 * @return true if it's a draw, false otherwise.
	 */
	public boolean isDraw() {
		return board.isDraw();
	}

	/**
	 * Resets the game board and sets the current player to 'X'.
	 */
	public void restartGame() {
		board = new Board();
		currentPlayer = GameSymbol.X;
	}

	/**
	 * Retrieves the symbol ('X' or 'O') at a given cell on the board.
	 * 
	 * @param row Row index of the cell.
	 * @param col Column index of the cell.
	 * @return Symbol at the cell.
	 */
	public String getBoardCellSymbol(int row, int col) {
		return board.getBoardCell(row, col).getSymbol();
	}

	/**
	 * Executes an AI move using the minMax algorithm.
	 * 
	 * @return Move chosen by the AI.
	 */
	public Move aiMove() {
		Move bestMove = ai.minMax(board, currentPlayer);

		if (bestMove != null) {
			board.placeMark(currentPlayer, bestMove.row, bestMove.col);
			switchPlayer();

			// Print the best move for the next player
			Move nextBestMove = provideHint();
			System.out.println(
					"Best move for " + currentPlayer + ": (" + nextBestMove.row + ", " + nextBestMove.col + ")");
		}

		return bestMove;
	}

	/**
	 * Returns the current player's symbol ('X' or 'O').
	 * 
	 * @return Symbol of the current player.
	 */
	public GameSymbol getCurrentPlayer() {
		return currentPlayer;
	}
}
