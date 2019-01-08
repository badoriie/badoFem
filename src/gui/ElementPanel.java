package gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ElementPanel extends JPanel {

	private static final long serialVersionUID = -2665276476504135635L;
	JTextField e;
	JTextField a;
	JLabel eModulus, area;

	public ElementPanel() {

		setLayout(new FlowLayout()); // set frame layout

		this.setBounds(630, 250, 130, 120);

		eModulus = new JLabel("E-Modulus:");
		add(eModulus);
		e = new JTextField("210000000000", 10);
		add(e);
		area = new JLabel("Cross-Section Area:");
		add(area);
		a = new JTextField("0.015", 10);

		add(a);

	}

	public double getE() {

		try {
			return Double.parseDouble(e.getText());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(null, "E-Modulus must be a float number", "Number format error",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);
			return 0;
		}

	}

	public double getA() {

		try {
			return Double.parseDouble(a.getText());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(null, "Area must be a float number", "Number format error",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);
			return 0;
		}

	}
}
