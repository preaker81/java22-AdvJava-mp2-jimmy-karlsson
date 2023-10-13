package main;

import javax.swing.SwingUtilities;

import view.TicTacToeGUI;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(TicTacToeGUI::new);

	}

}
