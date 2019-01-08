package fem;

import java.util.ArrayList;

import inf.jlinalg.Vector3D;
import inf.v3d.obj.Arrow;
import inf.v3d.obj.Cone;
import inf.v3d.obj.Cylinder;
import inf.v3d.obj.PolygonSet;
import inf.v3d.view.Viewer;

public class Visualizer {
	// attributes
	private double displacementScale = 1;
	private double constraintScale = 1;
	private double loadScale = 1;
	private double forceScale = 1;
	private Structure struct = new Structure();
	private Viewer viewer = new Viewer();
	private ArrayList<Cone> cones = new ArrayList<Cone>(); // to visualize the constraints
	private ArrayList<Arrow> arrows = new ArrayList<Arrow>(); // to visualize the forces
	private ArrayList<Cylinder> cylinders1 = new ArrayList<Cylinder>(); // to visualize the elements
	private ArrayList<Cylinder> cylinders2 = new ArrayList<Cylinder>(); // to visualize the deformation
	private ArrayList<PolygonSet> polygons = new ArrayList<PolygonSet>(); // to visualize the element force

	// constructor
	public Visualizer(Structure struct, Viewer viewer) { // to define a visualizer

		this.struct = struct;
		this.viewer = viewer;
	}

	// setConstraintScale method
	public void setConstraintScale(double c) { // to show better the constraints

		constraintScale = c;
	}

	// setForceScale method
	public void setForceScale(double f) { // to show better the force

		forceScale = f;
	}

	// setLoadScale method
	public void setLoadScale(double l) {// to show better the load

		loadScale = l;
	}

	// setDisplacement method
	public void setDisplacement(double d) { // to show the displacement better
		displacementScale = d;
	}

	// drawElements method
	public void drawElements() { // to draw elements

		for (int i = 0; i < this.struct.getNumberOfElements(); i++) {
			double[] p1 = this.struct.getElement(i).getNode1().getPosition().toArray();
			double[] p2 = this.struct.getElement(i).getNode2().getPosition().toArray();
			double radius = Math.sqrt(this.struct.getElement(i).getArea() / Math.PI);
			Cylinder cy = new Cylinder(p1, p2);
			cy.setRadius(radius);
			cylinders1.add(cy);
		}

		for (int k = 0; k < this.cylinders1.size(); k++) {

			viewer.addObject3D(cylinders1.get(k));

		}

	}

	// drawConstraints method
	public void drawConstraints() { // to draw constraints

		double X1, X2, X3;

		for (int i = 0; i < this.struct.getNumberOfNodes(); i++) {

			for (int j = 0; j < 3; j++) {

				if (this.struct.getNode(i).getConstraint().isFree(j) == false) {
					X1 = this.struct.getNode(i).getPosition().getX1();
					X2 = this.struct.getNode(i).getPosition().getX2();
					X3 = this.struct.getNode(i).getPosition().getX3();
					Cone cn = new Cone();
					if (j == 0) {
						cn.setDirection(1, 0, 0);
						cn.setCenter(X1 - constraintScale, X2, X3);
						cn.setColor("red");

					} else if (j == 1) {
						cn.setDirection(0, 1, 0);
						cn.setCenter(X1, X2 - constraintScale, X3);
						cn.setColor("green");

					} else {
						cn.setDirection(0, 0, 1);
						cn.setCenter(X1, X2, X3 - constraintScale);
						cn.setColor("blue");

					}

					cn.setRadius(constraintScale / 2);
					cn.setHeight(constraintScale);
					cones.add(cn);

				}

				for (int k = 0; k < this.cones.size(); k++) {

					viewer.addObject3D(cones.get(k));

				}

			}

		}

	}

	// drawLoadForces method
	public void drawLoadForces() {

		for (int i = 0; i < this.struct.getNumberOfNodes(); i++) {

			Arrow ar = new Arrow();

			double X1 = this.struct.getNode(i).getPosition().getX1();
			double X2 = this.struct.getNode(i).getPosition().getX2();
			double X3 = this.struct.getNode(i).getPosition().getX3();

			ar.setPoint2(X1, X2, X3);

			double f1 = this.struct.getNode(i).getForce().getComponent(0);
			double f2 = this.struct.getNode(i).getForce().getComponent(1);
			double f3 = this.struct.getNode(i).getForce().getComponent(2);

			ar.setPoint1(X1 - (f1 * loadScale), X2 - (f2 * loadScale), X3 - (f3 * loadScale));

			ar.setColor("black");

			arrows.add(ar);

		}

		for (int k = 0; k < this.arrows.size(); k++) {

			viewer.addObject3D(arrows.get(k));

		}

	}

	// draeElemetForces method
	public void drawElementForces() { // to draw element forces

		for (int i = 0; i < this.struct.getNumberOfElements(); i++) {
			Vector3D p1 = this.struct.getElement(i).getNode1().getPosition()
					.add(this.struct.getElement(i).getNode1().getDisplacement().multiply(displacementScale));
			Vector3D p2 = this.struct.getElement(i).getNode2().getPosition()
					.add(this.struct.getElement(i).getNode2().getDisplacement().multiply(displacementScale));

			Vector3D n = p1.vectorProduct(p2).normalize();

			PolygonSet ps = new PolygonSet();
			ps.insertVertex(p1.toArray(), this.struct.getElement(i).computeForce());
			ps.insertVertex(p2.toArray(), this.struct.getElement(i).computeForce());
			ps.insertVertex(p2.add(n.multiply(this.struct.getElement(i).computeForce() * forceScale)).toArray(),
					this.struct.getElement(i).computeForce());
			ps.insertVertex(p1.add(n.multiply(this.struct.getElement(i).computeForce() * forceScale)).toArray(),
					this.struct.getElement(i).computeForce());

			ps.polygonComplete();

			ps.setColoringByData(true);

			polygons.add(ps);
		}

		for (int k = 0; k < this.polygons.size(); k++) {

			viewer.addObject3D(polygons.get(k));

		}

	}

	// drawDisplacemets method
	public void drawDisplacemets() {

		for (int i = 0; i < this.struct.getNumberOfElements(); i++) {
			Vector3D p1 = this.struct.getElement(i).getNode1().getPosition()
					.add(this.struct.getElement(i).getNode1().getDisplacement().multiply(displacementScale));
			Vector3D p2 = this.struct.getElement(i).getNode2().getPosition()
					.add(this.struct.getElement(i).getNode2().getDisplacement().multiply(displacementScale));
			double radius = Math.sqrt(this.struct.getElement(i).getArea() / Math.PI);

			Cylinder cy = new Cylinder(p1.toArray(), p2.toArray());
			cy.setRadius(radius);

			cy.setColor("black");

			cylinders2.add(cy);

		}

		for (int k = 0; k < this.cylinders2.size(); k++) {

			viewer.addObject3D(cylinders2.get(k));

		}

	}

}
