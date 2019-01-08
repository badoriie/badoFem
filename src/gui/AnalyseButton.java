package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fem.Structure;
import fem.Visualizer;
import inf.v3d.view.Viewer;

public class AnalyseButton extends JButton {

	private Structure structure;

	private static final long serialVersionUID = -880339999973221286L;

	public AnalyseButton(Structure structure) {

		super("Analyse");

		this.structure = structure;
		this.setBounds(630, 380, 130, 47);

		ButtonHandler handler = new ButtonHandler();
		this.addActionListener(handler);
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			Viewer viewer = new Viewer();

			Visualizer viz = new Visualizer(structure, viewer);
			structure.solve();
			structure.printStructure();
			structure.printResults();
			viz.setConstraintScale(0.5);
			viz.setForceScale(0.00002);
			viz.setLoadScale(0.00002);
			viz.setDisplacement(5000);

			viz.drawElements();
			viz.drawConstraints();
			viz.drawLoadForces();
			viz.drawDisplacemets();
			viz.drawElementForces();
			viewer.setVisible(true);

		}

	}
}
