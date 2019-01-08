package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fem.*;

public class NodeEditor extends JFrame {

	private static final long serialVersionUID = 5555284312190316924L;
	private JLabel forceX, forceY;
	private JTextField xForce, yForce;
	private JCheckBox xConstraint, yConstraint;
	private JButton ok, cancel, delete;

	public NodeEditor(final int nodeID, final Structure struct) {

		super("NodeEditor");

		GridLayout gridLayout = new GridLayout(5, 2, 5, 5);
		setLayout(gridLayout);

		forceX = new JLabel(" Xforce: ");
		add(forceX);

		forceY = new JLabel(" Yforce: ");
		add(forceY);

		xForce = new JTextField(String.valueOf(struct.getNode(nodeID).getForce().getComponent(0)));
		add(xForce);

		yForce = new JTextField(String.valueOf(struct.getNode(nodeID).getForce().getComponent(1)));
		add(yForce);

		xConstraint = new JCheckBox("XConst");
		if (!struct.getNode(nodeID).getConstraint().isFree(0)) {

			xConstraint.setSelected(true);
		}

		add(xConstraint);
		yConstraint = new JCheckBox("YConst");
		if (!struct.getNode(nodeID).getConstraint().isFree(1)) {

			yConstraint.setSelected(true);
		}
		add(yConstraint);

		ok = new JButton("OK");
		add(ok);

		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				struct.getNode(nodeID).setForce(
						new Force(Double.parseDouble(xForce.getText()), Double.parseDouble(yForce.getText()), 0));

				struct.getNode(nodeID)
						.setConstraint(new Constraint(!xConstraint.isSelected(), !yConstraint.isSelected(), false));

				dispose();
			}
		});

		cancel = new JButton("Cancel");
		add(cancel);

		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});

		delete = new JButton("Delete");
		add(delete);

		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				ArrayList<Integer> temp = new ArrayList<Integer>(); // connected elements must be deleted at the same time
				for (int i = 0; i < struct.getNumberOfElements(); i++) {

					if (struct.getElement(i).getNode1() == struct.getNode(nodeID)
							|| struct.getElement(i).getNode2() == struct.getNode(nodeID)) {

						temp.add(i);

					}
				}

				Collections.reverse(temp);

				for (int k : temp) {

					struct.deleteElement(k);
				}

				struct.deleteNode(nodeID);
				dispose();
			}
		});

	}

}
