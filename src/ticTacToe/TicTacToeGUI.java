package ticTacToe;

import javax.swing.*;

import ticTacToe.TicTacToeAI.Move;
import ticTacToe.enums.Mark;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {

	private JFrame frame;
	private JButton[][] buttons;
	private JLabel statusLabel;
	private Board board;
	private TicTacToeAI ai;

	public TicTacToeGUI() {
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 350);
		frame.setLayout(new BorderLayout());

		board = new Board();
		ai = new TicTacToeAI();

		buttons = new JButton[3][3];
		JPanel boardPanel = new JPanel(new GridLayout(3, 3));

		for (int i = 0; i < 3; i++) {
		    for (int j = 0; j < 3; j++) {
		        buttons[i][j] = new JButton("-");
		        buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24)); // Bolder and larger font
		        buttons[i][j].addActionListener(new ButtonListener(i, j));
		        boardPanel.add(buttons[i][j]);
		    }
		}


		statusLabel = new JLabel("Player X's turn");
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(e -> restartGame());

		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(statusLabel, BorderLayout.NORTH);
		frame.add(restartButton, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private class ButtonListener implements ActionListener {
		private int row, col;

		public ButtonListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		    if (board.isValidMove(row, col)) {
		        board.placeMark(board.getCurrentPlayer(), row, col); // Player makes a move
		        updateBoard();

		        if (board.isWinner()) { // Check if player has won
		            statusLabel.setText((board.getCurrentPlayer() == Mark.X ? Mark.O : Mark.X) + " wins!");
		            disableButtons();
		            return;
		        }

		        if (board.isDraw()) {
		            statusLabel.setText("It's a draw!");
		            return;
		        }

		        aiMove();
		    }
		}

		private void aiMove() {
		    Move bestMove = ai.minMax(board);
		    board.placeMark(board.getCurrentPlayer(), bestMove.getRow(), bestMove.getCol());
		    updateBoard();

		    buttons[bestMove.getRow()][bestMove.getCol()].setBackground(Color.yellow); // Set the background color for the AI's last move

		    if (board.isWinner()) {
		        statusLabel.setText((board.getCurrentPlayer() == Mark.X ? Mark.O : Mark.X) + " wins!");
		        disableButtons();
		    } else if (board.isDraw()) {
		        statusLabel.setText("It's a draw!");
		    }
		}

	}

	private void updateBoard() {
	    // Reset all background colors first
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            buttons[i][j].setBackground(null);
	            // Update the text on the button to reflect the board's state
	            buttons[i][j].setText(board.getBoardCell(i, j).toString());
	        }
	    }
	}


	private void disableButtons() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}

	private void restartGame() {
		board = new Board();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(true);
				buttons[i][j].setText("-");
			}
		}
		statusLabel.setText("Player X's turn");
	}
}
