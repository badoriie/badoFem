package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fem.Structure;

public class ResetButton extends JButton {

	private static final long serialVersionUID = 3682835811924784774L;

	private Structure structure;

	public ResetButton(Structure structure) {

		super("Reset");

		this.structure = structure;
		this.setBounds(500, 15, 50, 40);

		ButtonHandler handler = new ButtonHandler();
		this.addActionListener(handler);
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			structure.clear();

		}

	}
}
