package main;

import javax.swing.SwingUtilities;

import ticTacToe.TicTacToeGUI;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(TicTacToeGUI::new);

	}

}
