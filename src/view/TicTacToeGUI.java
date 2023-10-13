package view;

import javax.swing.*;
import controller.TicTacToeController;
import model.TicTacToeAI.Move;
import utils.enums.GameSymbol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The TicTacToeGUI class represents the graphical interface for the Tic-Tac-Toe
 * game.
 */
public class TicTacToeGUI {
	private TicTacToeController controller;
	private JFrame frame;
	private JButton[][] buttons;
	private JLabel statusLabel;

	/**
	 * Constructor to initialize the game frame and components.
	 */
	public TicTacToeGUI() {
		initializeFrame();
		initializeGame();
		addUIComponents();
		frame.setVisible(true);
	}

	/**
	 * Initialize the main frame for the game.
	 */
	private void initializeFrame() {
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 350);
		frame.setLayout(new BorderLayout());
	}

	/**
	 * Initialize the game logic controller.
	 */
	private void initializeGame() {
		controller = new TicTacToeController();
	}

	/**
	 * Add all UI components to the frame.
	 */
	private void addUIComponents() {
		addBoardPanel();
		addStatusLabel();
		addControlButtons();
	}

	/**
	 * Add the game board to the frame.
	 */
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

	/**
	 * Create a button for the board.
	 * 
	 * @param row The row index for the button.
	 * @param col The column index for the button.
	 * @return The button created.
	 */
	private JButton createBoardButton(int row, int col) {
		JButton button = new JButton("-");
		button.setFont(new Font("Arial", Font.BOLD, 24));
		button.addActionListener(new ButtonListener(row, col));
		return button;
	}

	/**
	 * Add a label to indicate the game status.
	 */
	private void addStatusLabel() {
		statusLabel = new JLabel("Player X's turn");
		frame.add(statusLabel, BorderLayout.NORTH);
	}

	/**
	 * Add control buttons like 'Restart' and 'Get Hint' to the frame.
	 */
	private void addControlButtons() {
		JPanel controlPanel = new JPanel(new FlowLayout());
		JButton restartButton = new JButton("Restart");
		JButton hintButton = new JButton("Get Hint");

		// Add action listeners to control buttons
		restartButton.addActionListener(e -> restartGame());
		hintButton.addActionListener(e -> provideHint());

		controlPanel.add(restartButton);
		controlPanel.add(hintButton);

		frame.add(controlPanel, BorderLayout.SOUTH);
	}

	/**
	 * Provide a hint by highlighting the best move.
	 */
	private void provideHint() {
		Move bestMove = controller.provideHint();
		int bestRow = bestMove.getRow();
		int bestCol = bestMove.getCol();
		buttons[bestRow][bestCol].setBackground(Color.green);
	}

	/**
	 * Inner class to handle button click events.
	 */
	private class ButtonListener implements ActionListener {
		private int row, col;

		public ButtonListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		/**
		 * Handle button click event to make a move or trigger AI.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (controller.isValidMove(row, col)) {
				controller.makeMove(row, col);
				updateBoard();
				if (controller.isWinner()) {
					GameSymbol previousPlayer = (controller.getCurrentPlayer() == GameSymbol.X) ? GameSymbol.O
							: GameSymbol.X;
					statusLabel.setText(previousPlayer + " wins!");
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

		/**
		 * Trigger the AI to make a move.
		 */
		private void aiMove() {
			// Delay the AI move to make the game feel more natural
			Timer timer = new Timer(1000, e -> {
				Move bestMove = controller.aiMove();
				controller.makeMove(bestMove.getRow(), bestMove.getCol());
				updateBoard();
				buttons[bestMove.getRow()][bestMove.getCol()].setBackground(Color.yellow);
				if (controller.isWinner()) {
					statusLabel.setText(
							(controller.getCurrentPlayer() == GameSymbol.X ? GameSymbol.O : GameSymbol.X) + " wins!");
					disableButtons();
				} else if (controller.isDraw()) {
					statusLabel.setText("It's a draw!");
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}

	/**
	 * Update the game board after a move is made.
	 */
	private void updateBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setBackground(null);
				buttons[i][j].setText(controller.getBoardCellSymbol(i, j));
			}
		}
		statusLabel.setText("Player " + controller.getCurrentPlayer().getSymbol() + "'s turn");
	}

	/**
	 * Disable all buttons on the game board.
	 */
	private void disableButtons() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}

	/**
	 * Restart the game and reset all components.
	 */
	private void restartGame() {
		controller.restartGame();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(true);
				buttons[i][j].setText("-");
				buttons[i][j].setBackground(null);
			}
		}
		statusLabel.setText("Player " + GameSymbol.X.getSymbol() + "'s turn");
	}
}
