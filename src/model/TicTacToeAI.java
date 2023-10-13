package model;

import java.util.ArrayList;
import java.util.List;
import utils.enums.Mark;

public class TicTacToeAI {

	public Move minMax(Board board) {

		if (board.isXWinner()) {
			return new Move(1);
		}
		if (board.isOWinner()) {
			return new Move(-1);
		}
		if (!board.hasEmptyCells()) {
			return new Move(0);
		}

		List<Move> moves = evaluateMoves(board);

		int bestMoveIndex = board.getCurrentPlayer() == Mark.X ? findBestMoveForMax(moves) : findBestMoveForMin(moves);

		return moves.get(bestMoveIndex);
	}

	private List<Move> evaluateMoves(Board board) {
		List<Move> moves = new ArrayList<>();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board.isValidMove(row, col)) {
					moves.add(evaluateMove(board, row, col));
				}
			}
		}

		return moves;
	}

	private Move evaluateMove(Board board, int row, int col) {
		Move move = new Move(row, col);
		Board boardCopy = board.copyBoard();

		boardCopy.placeMark(board.getCurrentPlayer(), row, col);
		move.setScore(minMax(boardCopy).getScore());

		board.setEmpty(row, col);
		return move;
	}

	private int findBestMoveForMax(List<Move> moves) {
		return findBestMove(moves, Integer.MIN_VALUE, true);
	}

	private int findBestMoveForMin(List<Move> moves) {
		return findBestMove(moves, Integer.MAX_VALUE, false);
	}

	private int findBestMove(List<Move> moves, int initialBestScore, boolean isMax) {
		int bestMove = -1;
		int bestScore = initialBestScore;

		for (int i = 0; i < moves.size(); i++) {
			int currentScore = moves.get(i).getScore();

			if (isMax ? currentScore > bestScore : currentScore < bestScore) {
				bestScore = currentScore;
				bestMove = i;
			}
		}

		return bestMove;
	}

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
