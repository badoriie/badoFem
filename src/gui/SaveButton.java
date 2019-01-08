package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import fem.Structure;

public class SaveButton extends JButton {

	private static final long serialVersionUID = 3682835811924784774L;

	private Structure structure;

	public SaveButton(Structure structure) {

		super("Save");

		this.structure = structure;
		this.setBounds(380, 15, 50, 40);

		ButtonHandler handler = new ButtonHandler();
		this.addActionListener(handler);
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			JFileChooser save = new JFileChooser();
			int retrival = save.showSaveDialog(null);

			if (retrival == JFileChooser.APPROVE_OPTION) {

				structure.save(save.getSelectedFile() + ".dat");

			}

		}

	}
}
