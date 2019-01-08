package gui;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setLayout(null);
		mainFrame.setSize(800, 500); // set frame size
		mainFrame.setResizable(false);
		mainFrame.setVisible(true); // display frame

	}

}
