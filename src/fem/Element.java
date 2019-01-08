package fem;

import java.io.Serializable;

import inf.jlinalg.*;
import inf.text.ArrayFormat;

public class Element implements Serializable {

	private static final long serialVersionUID = 1L;
	// attributes
	private double area; // the cross-section area of truss
	private double eModulus; // the elastic coefficient of truss element
	private int[] dofNumbers = new int[6]; // a truss has 6 DOFs
	private Node node1, node2; // each element has two nodes

	// constructor
	public Element(double e, double a, Node n1, Node n2) { // to define an element

		this.area = a;
		this.eModulus = e;
		this.node1 = n1;
		this.node2 = n2;

	}

	// computeStiffnessMatrix() method
	public IMatrix computeStiffnessMatrix() { // this method compute the stiffness matrix of the element

		double c = (this.eModulus * this.area) / Math.pow(this.getLength(), 3);
		Vector3D X1 = this.node1.getPosition(); // node1 position
		Vector3D X2 = this.node2.getPosition(); // node2 position
		Vector3D X2_X1 = X2.subtract(X1);
		IMatrix kptmp = X2_X1.dyadicProduct(X2_X1);
		IMatrix kntmp = X2_X1.multiply(-1).dyadicProduct(X2_X1);

		IMatrix ke = new Array2DMatrix(6, 6);

		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				ke.set(i, j, c * kptmp.get(i, j));
			}
		}
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				ke.set(i, j + 3, c * kntmp.get(i, j));
			}
		}
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				ke.set(i + 3, j, c * kntmp.get(i, j));
			}
		}
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				ke.set(i + 3, j + 3, c * kptmp.get(i, j));
			}
		}

		return ke; // return stiffness matrix of the element
	}

	// enumerateDOFs method
	public void enumerateDOFs() { // we have to enumerate the DOFs in our algorithm
		for (int i = 0; i <= 2; i++) {
			this.dofNumbers[i] = this.node1.getDOFNumbers()[i];
			this.dofNumbers[i + 3] = this.node2.getDOFNumbers()[i];
		}

	}

	// getDOFNumbers method
	public int[] getDOFNumbers() {
		return this.dofNumbers;

	}

	// computeForce method
	public double computeForce() { // compute element axial force

		double c = (this.eModulus * this.area) / this.getLength();
		Vector3D X1 = this.node1.getPosition(); // node1 position
		Vector3D X2 = this.node2.getPosition(); // node2 position
		Vector3D X2_X1 = X2.subtract(X1);
		Vector3D Ti = X2_X1.multiply(1 / X2_X1.norm2());
		double u11 = Ti.scalarProduct(node1.getDisplacement());
		double u12 = Ti.scalarProduct(node2.getDisplacement());

		return c * (u12 - u11);

	}

	// getE1 method
	public Vector3D getE1() { // normal vector of cross-section area
		return this.node1.getPosition().subtract(this.node2.getPosition()).normalize();
	}

	// getLength
	public double getLength() { // returns the length of the element

		return this.node1.getPosition().subtract(this.node2.getPosition()).norm2();

	}

	// getNode1 method
	public Node getNode1() { // returns the first node of the element

		return this.node1;
	}

	// getNode2 method
	public Node getNode2() { // returns the second node of the element

		return this.node2;
	}

	// getArea method
	public double getArea() { // returns the cross-section area of the element

		return this.area;
	}

	// setArea method

	public void setArea(double a) {

		this.area = a;

	}

	// getEmodulus method
	public double getEModulus() { // returns the element elastic coefficient

		return this.eModulus;
	}

	// setEmodulus method
	public void setEModulus(double e) {

		this.eModulus = e;
	}

	// print method
	public void print() { // prints the elements properties
		double[] s = { this.getEModulus(), this.getArea(), this.getLength() };
		System.out.println(ArrayFormat.format(s));
	}

	// print2 method (for structure print)
	public void print2(int k) { // in order to have well-posed print structure
		double[] s = { this.getEModulus(), this.getArea(), this.getLength() };
		System.out.println(k + ArrayFormat.format(s));
	}

}
