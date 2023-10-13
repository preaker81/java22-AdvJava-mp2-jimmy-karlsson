package controller;

import model.Board;
import model.TicTacToeAI;
import model.TicTacToeAI.Move;
import utils.enums.Mark;

public class TicTacToeController {
	private Board board;
	private TicTacToeAI ai;

	public TicTacToeController() {
		this.board = new Board();
		this.ai = new TicTacToeAI();
	}

	public Move provideHint() {
		return ai.minMax(board);
	}

	public boolean isValidMove(int row, int col) {
		return board.isValidMove(row, col);
	}

	public void makeMove(int row, int col) {
		board.placeMark(board.getCurrentPlayer(), row, col);
	}

	public boolean isWinner() {
		return board.isWinner();
	}

	public boolean isDraw() {
		return board.isDraw();
	}

	public Mark getCurrentPlayer() {
		return board.getCurrentPlayer();
	}

	public void restartGame() {
		board = new Board();
	}

	public String getBoardCellSymbol(int row, int col) {
		return board.getBoardCell(row, col).getSymbol();
	}

	public Move aiMove() {
		return ai.minMax(board);
	}
}
