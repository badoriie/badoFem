package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fem.*;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 7722012041466274879L;

	private Structure struct;
	private Point pointStart = null;
	private Point pointEnd = null;
	private Point node = null;

	private JLabel statusBar, x, y;
	private JCheckBox grid, deformation;

	private int panelWidth = 550;
	private int panelHeight = 344;
	private int node1ID;
	private int sc = 5; // sclaeCoefficient
	private int radiusCoef = 300; // radius coefficient
	private int nRad =10; //radius of a node
	private double deformScale = 5000; // scale for deformation

	private NodePanel nodePanel;
	private ElementPanel elementPanel;

	public DrawingPanel(Structure struct, NodePanel nodePanel, ElementPanel elementPanel) {

		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(50, 80, panelWidth, panelHeight);
		this.struct = struct;
		this.nodePanel = nodePanel;
		this.elementPanel = elementPanel;

		statusBar = new JLabel();
		statusBar.setBounds(5, 326, 300, 20);
		this.add(statusBar);

		y = new JLabel("Y");
		y.setBounds(12, 266, 300, 20);
		y.setForeground(Color.red);

		this.add(y);

		x = new JLabel("X");
		x.setBounds(50, 305, 300, 20);
		x.setForeground(Color.green);
		this.add(x);

		grid = new JCheckBox("Grid");
		grid.setBounds(490, 320, 60, 20);
		this.add(grid);

		deformation = new JCheckBox("Deformation");
		deformation.setBounds(380, 320, 130, 20);
		this.add(deformation);

	}

	{
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (e.isAltDown()) { // node making part
					node = e.getPoint();
					struct.addNode((double) (node.x - (panelWidth / 2)) / sc,
							(double) ((panelHeight / 2) - node.y) / sc, 0);

					struct.getNode(struct.getNumberOfNodes() - 1).setConstraint(nodePanel.constraint);
					struct.getNode(struct.getNumberOfNodes() - 1).setForce(nodePanel.getForce());

					repaint();
				}

				if (e.isMetaDown()) { // node edit part

					for (int i = 0; i < struct.getNumberOfNodes(); i++) {
						if (e.getX() - (panelWidth / 2) <= struct.getNode(i).getPosition().getX1() * sc + nRad
								&& e.getX() - (panelWidth / 2) >= struct.getNode(i).getPosition().getX1() * sc - nRad) {
							if ((panelHeight / 2) - e.getY() <= struct.getNode(i).getPosition().getX2() * sc + nRad
									&& (panelHeight / 2) - e.getY() >= struct.getNode(i).getPosition().getX2() * sc
											- nRad) {
								ArrayList<Integer> temp = new ArrayList<Integer>(); // this part is for fixing a bug of
																					// main library
								for (int k = 0; k < struct.getNumberOfElements(); k++) {

									if (struct.getElement(k).getLength() == 0) {
										temp.add(k);

									}
								}

								for (int t : temp) {
									struct.deleteElement(t);
								}

								NodeEditor nd = new NodeEditor(i, struct);
								nd.setSize(350, 200);
								nd.setVisible(true);

							}

						}

					}
				}

				if (e.isMetaDown()) { // element editor part

					for (int j = 0; j < struct.getNumberOfElements(); j++) {

						if (e.getX()
								- (panelWidth / 2) <= ((struct.getElement(j).getNode2().getPosition().getX1()
										+ struct.getElement(j).getNode1().getPosition().getX1()) * sc / 2) + nRad
								&& e.getX()
										- (panelWidth / 2) >= ((struct.getElement(j).getNode2().getPosition().getX1()
												+ struct.getElement(j).getNode1().getPosition().getX1()) * sc / 2)
												- nRad) {
							if ((panelHeight / 2)
									- e.getY() <= ((struct.getElement(j).getNode2().getPosition().getX2()
											+ struct.getElement(j).getNode1().getPosition().getX2()) * sc / 2) + nRad
									&& (panelHeight / 2)
											- e.getY() >= ((struct.getElement(j).getNode2().getPosition().getX2()
													+ struct.getElement(j).getNode1().getPosition().getX2()) * sc / 2)
													- nRad) {

								ElementEditor ed = new ElementEditor(j, struct);
								ed.setSize(350, 200);
								ed.setVisible(true);

							}

						}

					}

				}

			}

			public void mousePressed(MouseEvent e) {

				for (int i = 0; i < struct.getNumberOfNodes(); i++) {
					if (e.getX() - (panelWidth / 2) <= struct.getNode(i).getPosition().getX1() * sc + nRad
							&& e.getX() - (panelWidth / 2) >= struct.getNode(i).getPosition().getX1() * sc - nRad) {
						if ((panelHeight / 2) - e.getY() <= struct.getNode(i).getPosition().getX2() * sc + nRad
								&& (panelHeight / 2) - e.getY() >= struct.getNode(i).getPosition().getX2() * sc - nRad) {

							ArrayList<Integer> temp = new ArrayList<Integer>();// for fixing a bug
							for (int k = 0; k < struct.getNumberOfElements(); k++) {

								if (struct.getElement(k).getLength() == 0) {
									temp.add(k);

								}
							}

							for (int t : temp) {
								struct.deleteElement(t);
							}

							pointStart = new Point(
									(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
									(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc));
							node1ID = i;

						}

					}

				}

			}

			public void mouseReleased(MouseEvent e) {

				for (int i = 0; i < struct.getNumberOfNodes(); i++) {
					if (e.getX() - (panelWidth / 2) <= struct.getNode(i).getPosition().getX1() * sc + nRad
							&& e.getX() - (panelWidth / 2) >= struct.getNode(i).getPosition().getX1() * sc - nRad) {
						if ((panelHeight / 2) - e.getY() <= struct.getNode(i).getPosition().getX2() * sc + nRad
								&& (panelHeight / 2) - e.getY() >= struct.getNode(i).getPosition().getX2() * sc - nRad) {

							pointEnd = new Point(
									(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
									(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2()) * sc);

							struct.addElement(elementPanel.getE(), elementPanel.getA(), node1ID, i);

						}

					}

				}

				pointStart = null;

			}

			public void mouseExited(MouseEvent event) {
				statusBar.setText("");

			}

		});
		addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseMoved(MouseEvent e) {
				pointEnd = e.getPoint();
				statusBar.setText(String.format("[%f, %f]", (float) (e.getX() - (panelWidth / 2)) / sc,
						(float) ((panelHeight / 2) - e.getY()) / sc));
				repaint();
			}

			public void mouseDragged(MouseEvent e) {

				pointEnd = e.getPoint();
				statusBar.setText(String.format("[%f, %f]", (float) (e.getX() - (panelWidth / 2)) / sc,
						(float) ((panelHeight / 2) - e.getY()) / sc));
				repaint();
			}

		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		// for drawing the coordinate system
		g.setColor(Color.BLACK);
		g.drawLine(panelWidth / 2, 0, panelWidth / 2, panelHeight);
		g.drawLine(0, panelHeight / 2, panelWidth, panelHeight / 2);

		g.setColor(Color.red);
		g.drawLine(15, 315, 15, 284);
		g.setColor(Color.green);
		g.drawLine(15, 315, 46, 315);

		g.setColor(Color.cyan);
		if (grid.isSelected()) { // for drawing grid

			for (int i = 1; i <= 5; i++) {

				g.drawLine((panelWidth / 2) + 50 * i, 0, (panelWidth / 2) + 50 * i, panelHeight);
				g.drawLine((panelWidth / 2) - 50 * i, 0, (panelWidth / 2) - 50 * i, panelHeight);

			}

			for (int j = 1; j <= 3; j++) {

				g.drawLine(0, (panelHeight / 2) + 50 * j, panelWidth, (panelHeight / 2) + 50 * j);
				g.drawLine(0, (panelHeight / 2) - 50 * j, panelWidth, (panelHeight / 2) - 50 * j);
			}

		}

		if (pointStart != null) { // for drawing the elements

			g.setColor(Color.black);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);

		}

		for (int i = 0; i < struct.getNumberOfElements(); i++) {// drawing element with thickness

			g.setColor(Color.black);

			int j = (int) Math.ceil(radiusCoef * Math.sqrt(struct.getElement(i).getArea() / Math.PI) /10); 

			double angle = Math.atan((struct.getElement(i).getNode1().getPosition().getX2()
					- struct.getElement(i).getNode2().getPosition().getX2())
					/ (struct.getElement(i).getNode1().getPosition().getX1()
							- struct.getElement(i).getNode2().getPosition().getX1()));

			int jcos = (int) (j * Math.cos(angle));
			int jsin = (int) (j * Math.sin(angle));

			//for calibrating the thickness
			if (Math.cos(angle) == 1)
				jcos = jcos - 1;
			if (Math.cos(angle) == -1)
				jcos = jcos + 1;
			if (Math.sin(angle) == 1)
				jsin = jsin - 1;
			if (Math.sin(angle) == -1)
				jsin = jsin + 1;

			g.drawLine(((int) (struct.getElement(i).getNode1().getPosition().getX1() * sc) + (panelWidth / 2)) + jsin,
					((panelHeight / 2) - (int) (struct.getElement(i).getNode1().getPosition().getX2() * sc)) + jcos,
					((int) (struct.getElement(i).getNode2().getPosition().getX1() * sc) + (panelWidth / 2)) + jsin,
					((panelHeight / 2) - (int) (struct.getElement(i).getNode2().getPosition().getX2() * sc) + jcos));

			g.drawLine(((int) (struct.getElement(i).getNode1().getPosition().getX1() * sc) + (panelWidth / 2)) - jsin,
					((panelHeight / 2) - (int) (struct.getElement(i).getNode1().getPosition().getX2() * sc)) - jcos,
					((int) (struct.getElement(i).getNode2().getPosition().getX1() * sc) + (panelWidth / 2)) - jsin,
					((panelHeight / 2) - (int) (struct.getElement(i).getNode2().getPosition().getX2() * sc) - jcos));

			g.setColor(Color.red);
			g.fillOval(
					(int) ((struct.getElement(i).getNode2().getPosition().getX1()
							+ struct.getElement(i).getNode1().getPosition().getX1()) * sc / 2) + (panelWidth / 2) - nRad,
					(panelHeight / 2) - (int) ((struct.getElement(i).getNode2().getPosition().getX2()
							+ struct.getElement(i).getNode1().getPosition().getX2()) * sc / 2) - nRad,
					nRad*2, nRad*2);

		}

		if (deformation.isSelected()) {
			struct.solve();
			for (int i = 0; i < struct.getNumberOfElements(); i++) { // for drawing deformation
				g.setColor(Color.yellow);

				g.drawLine(
						((int) ((struct.getElement(i).getNode1().getPosition().getX1()
								+ (struct.getElement(i).getNode1().getDisplacement()).multiply(deformScale).getX1())
								* sc) + (panelWidth / 2)),
						((panelHeight / 2) - (int) ((struct.getElement(i).getNode1().getPosition().getX2()
								+ (struct.getElement(i).getNode1().getDisplacement()).multiply(deformScale).getX2())
								* sc)),
						((int) ((struct.getElement(i).getNode2().getPosition().getX1()
								+ (struct.getElement(i).getNode2().getDisplacement()).multiply(deformScale).getX1())
								* sc) + (panelWidth / 2)),
						((panelHeight / 2) - (int) ((struct.getElement(i).getNode2().getPosition().getX2()
								+ (struct.getElement(i).getNode2().getDisplacement().multiply(deformScale)).getX2())
								* sc)));

			}

		}

		for (int i = 0; i < struct.getNumberOfNodes(); i++) {// for drawing the constraints

			g.setColor(Color.red);
			if (struct.getNode(i).getConstraint().isFree(0) == false
					&& struct.getNode(i).getConstraint().isFree(1) == true) {

				g.drawLine((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc),
						(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2) - nRad*2,
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc));
			}

			if (struct.getNode(i).getConstraint().isFree(0) == true
					&& struct.getNode(i).getConstraint().isFree(1) == false) {

				g.drawLine((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc),
						(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc) + nRad*2);
			}

			if (struct.getNode(i).getConstraint().isFree(0) == false
					&& struct.getNode(i).getConstraint().isFree(1) == false) {

				g.drawLine((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc),
						(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2) - nRad*2,
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc));

				g.drawLine((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc),
						(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
						(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc) + nRad*2);

			}

			// for drawing the loads
			g.setColor(Color.blue);
			g.drawLine((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2),
					(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc),
					(int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2)
							- (int) Math.ceil(struct.getNode(i).getForce().getComponent(0) * 3 / 10000),
					(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc)
							+ (int) Math.ceil(struct.getNode(i).getForce().getComponent(1) * 3 / 10000));

			// for drawing the nodes
			g.setColor(Color.black);
			g.fillOval((int) (struct.getNode(i).getPosition().getX1() * sc) + (panelWidth / 2) - nRad,
					(panelHeight / 2) - (int) (struct.getNode(i).getPosition().getX2() * sc) - nRad, nRad*2, nRad*2);

		}

	}

}
