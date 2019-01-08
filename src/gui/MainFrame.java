package gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import fem.*;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4567480330053559899L; // serialVersionUID

	private DrawingPanel drawingPanel; // panel in which mouse events will occur

	private NodePanel nodePanel;

	private ElementPanel elementPanel;

	private AnalyseButton analyseButton;

	private SaveButton saveButton;

	private Loadbutton loadbutton;

	private ResetButton resetbutton;

	private JLabel badoFEM, description, guide;

	private Structure structure = new Structure();

	// Constructor
	public MainFrame() {

		super("BadoFEM");

		badoFEM = new JLabel("BadoFEM");
		badoFEM.setBounds(50, 25, 300, 30);
		badoFEM.setFont(new Font("Arial", Font.BOLD, 41));
		add(badoFEM);

		description = new JLabel(
				"This software can help you to model and analyse 2D linear Truss structures using FEM");
		description.setBounds(50, 52, 600, 30);
		add(description);

		guide = new JLabel(
				"GUIDE: Middle-click makes a new node, use left-click to connncet the nodes and right-click to edit");
		guide.setBounds(50, 430, 750, 30);
		add(guide);

		nodePanel = new NodePanel();
		add(nodePanel);

		elementPanel = new ElementPanel();
		add(elementPanel);

		drawingPanel = new DrawingPanel(structure, nodePanel, elementPanel); // create panel

		add(drawingPanel); // add panel to JFrame

		analyseButton = new AnalyseButton(structure);
		add(analyseButton);

		saveButton = new SaveButton(structure);
		add(saveButton);

		loadbutton = new Loadbutton(structure);
		add(loadbutton);

		resetbutton = new ResetButton(structure);
		add(resetbutton);

	}// end of the constructor

}
