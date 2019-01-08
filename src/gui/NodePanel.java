package gui;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import fem.Constraint;
import fem.Force;

public class NodePanel extends JPanel {

	private JTextField forceX;
	private JTextField forceY;
	private JCheckBox constraintX;
	private JCheckBox constraintY;
	private JLabel constraints, xForce, yForce;
	Constraint constraint = new Constraint(true, true, false);

	private static final long serialVersionUID = 8653822099248982125L;

	public NodePanel() {

		setLayout(new FlowLayout()); // set frame layout

		this.setBounds(630, 50, 130, 200);

		xForce = new JLabel("XForce:");
		add(xForce);

		forceX = new JTextField("100000", 10);
		add(forceX);

		yForce = new JLabel("YForce:");
		add(yForce);

		forceY = new JTextField("-50000", 10);

		add(forceY);

		constraints = new JLabel("CONSTRAINTS");
		add(constraints);

		constraintX = new JCheckBox("XConst");
		constraintY = new JCheckBox("YConst");
		add(constraintX);
		add(constraintY);

		CheckBoxHandler handler = new CheckBoxHandler();
		constraintX.addItemListener(handler);
		constraintY.addItemListener(handler);

	}

	private class CheckBoxHandler implements ItemListener {
		// respond to checkbox events
		public void itemStateChanged(ItemEvent event) {
			if (constraintX.isSelected() && constraintY.isSelected()) {

				constraint = new Constraint(false, false, false);

			} else if (constraintX.isSelected()) {

				constraint = new Constraint(false, true, false);

			} else if (constraintY.isSelected()) {

				constraint = new Constraint(true, false, false);

			}

			else

			{
				constraint = new Constraint(true, true, false);

			}

		}

	}

	public Force getForce() {

		try {
			return new Force(Double.parseDouble(forceX.getText()), Double.parseDouble(forceY.getText()), 0);
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(null, "Force input must be a float number", "Number format error",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);
			return null;
		}

	}

}
