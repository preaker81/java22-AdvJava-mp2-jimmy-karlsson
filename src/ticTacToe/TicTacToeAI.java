package ticTacToe;

import java.util.ArrayList;
import java.util.List;

import ticTacToe.enums.Mark;

public class TicTacToeAI {

	public Move minMax(Board board) {
		System.out.println("Evaluating move for player: " + board.getCurrentPlayer());
		
		if (board.isXWinner()) {
			return new Move(1); // X has won
		}
		if (board.isOWinner()) {
			return new Move(-1); // O has won
		}

		if (!board.hasEmptyCells()) {
			return new Move(0); // It's a draw
		}

		List<Move> moves = new ArrayList<>();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board.isValidMove(row, col)) {
					Move move = new Move(row, col);

					Board boardCopy = board.copyBoard();

					if (board.getCurrentPlayer() == Mark.X) {
					    boardCopy.placeMark(Mark.X, row, col);
					    move.setScore(minMax(boardCopy).getScore());
					} else {
					    boardCopy.placeMark(Mark.O, row, col);
					    move.setScore(minMax(boardCopy).getScore());
					}

					board.setEmpty(row, col);
					moves.add(move);
				}
			}
		}

        System.out.println("Total moves evaluated: " + moves.size());
        
		int bestMoveIndex = board.getCurrentPlayer() == Mark.X ? findMax(moves) : findMin(moves);
		return moves.get(bestMoveIndex);
	}
	
	private int findMax(List<Move> moves) {
		int bestMove = -1;
		int bestScore = Integer.MIN_VALUE;
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i).getScore() > bestScore) {
				bestScore = moves.get(i).getScore();
				bestMove = i;
			}
		}
		return bestMove;
	}

	private int findMin(List<Move> moves) {
		int bestMove = -1;
		int bestScore = Integer.MAX_VALUE;
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i).getScore() < bestScore) {
				bestScore = moves.get(i).getScore();
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
