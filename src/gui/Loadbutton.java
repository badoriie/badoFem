package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import fem.Structure;


public class Loadbutton extends JButton {

	private static final long serialVersionUID = 3682835811924784774L;
	
	private Structure structure;

	public Loadbutton(Structure structure) {

		super("Load");

		this.structure = structure;
		this.setBounds(440, 15, 50, 40);

		ButtonHandler handler = new ButtonHandler();
		this.addActionListener(handler);
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			JFileChooser load = new JFileChooser();
			int retrival = load.showOpenDialog(null);
			
	        
			if (retrival == JFileChooser.APPROVE_OPTION) {
			
		
		    structure.load(load.getSelectedFile().getAbsolutePath());
			}

		}

	}
}
