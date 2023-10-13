package view;

import javax.swing.*;
import controller.TicTacToeController;
import model.TicTacToeAI.Move;
import utils.enums.Mark;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {
	private TicTacToeController controller;
	private JFrame frame;
	private JButton[][] buttons;
	private JLabel statusLabel;

	public TicTacToeGUI() {
		initializeFrame();
		initializeGame(); // Initialize game
		addUIComponents();
		frame.setVisible(true);
	}

	private void initializeFrame() {
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 350);
		frame.setLayout(new BorderLayout());
	}

	private void initializeGame() { // Initialize the controller
		controller = new TicTacToeController();
	}

	private void addUIComponents() {
		addBoardPanel();
		addStatusLabel();
		addControlButtons();
	}

	private void addBoardPanel() {
		buttons = new JButton[3][3];
		JPanel boardPanel = new JPanel(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = createBoardButton(i, j);
				boardPanel.add(buttons[i][j]);
			}
		}
		frame.add(boardPanel, BorderLayout.CENTER);
	}

	private JButton createBoardButton(int row, int col) {
		JButton button = new JButton("-");
		button.setFont(new Font("Arial", Font.BOLD, 24));
		button.addActionListener(new ButtonListener(row, col));
		return button;
	}

	private void addStatusLabel() {
		statusLabel = new JLabel("Player X's turn");
		frame.add(statusLabel, BorderLayout.NORTH);
	}

	private void addControlButtons() {
		JPanel controlPanel = new JPanel(new FlowLayout());
		JButton restartButton = new JButton("Restart");
		JButton hintButton = new JButton("Get Hint");

		restartButton.addActionListener(e -> restartGame());
		hintButton.addActionListener(e -> provideHint());

		controlPanel.add(restartButton);
		controlPanel.add(hintButton);

		frame.add(controlPanel, BorderLayout.SOUTH);
	}

	private void provideHint() {
		Move bestMove = controller.provideHint();
		int bestRow = bestMove.getRow();
		int bestCol = bestMove.getCol();

		buttons[bestRow][bestCol].setBackground(Color.green);
	}

	private class ButtonListener implements ActionListener {
		private int row, col;

		public ButtonListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (controller.isValidMove(row, col)) { // Use controller
				controller.makeMove(row, col); // Use controller
				updateBoard();

				if (controller.isWinner()) {
					statusLabel.setText((controller.getCurrentPlayer() == Mark.X ? Mark.O : Mark.X) + " wins!");
					disableButtons();
					return;
				}

				if (controller.isDraw()) {
					statusLabel.setText("It's a draw!");
					return;
				}

				aiMove();
			}
		}

		private void aiMove() {
			Timer timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Move bestMove = controller.aiMove(); // Use controller here to get the best move
					controller.makeMove(bestMove.getRow(), bestMove.getCol()); // Use controller to make the move
					updateBoard();
					buttons[bestMove.getRow()][bestMove.getCol()].setBackground(Color.yellow);

					if (controller.isWinner()) { // Use controller to check for a winner
						statusLabel.setText((controller.getCurrentPlayer() == Mark.X ? Mark.O : Mark.X) + " wins!");
						disableButtons();
					} else if (controller.isDraw()) { // Use controller to check for a draw
						statusLabel.setText("It's a draw!");
					}
				}
			});
			timer.setRepeats(false);
			timer.start();
		}

	}

	private void updateBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setBackground(null);
				buttons[i][j].setText(controller.getBoardCellSymbol(i, j)); // Use controller
			}
		}
		statusLabel.setText("Player " + controller.getCurrentPlayer().getSymbol() + "'s turn"); // Use controller
	}

	private void disableButtons() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}

	private void restartGame() {
		controller.restartGame(); // Use controller to restart game
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(true);
				buttons[i][j].setText("-");
				buttons[i][j].setBackground(null);
			}
		}
		statusLabel.setText("Player " + Mark.X.getSymbol() + "'s turn");
	}

}
