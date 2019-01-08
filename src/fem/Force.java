package fem;

import java.io.Serializable;

import inf.text.ArrayFormat;

public class Force implements Serializable {

	private static final long serialVersionUID = 1L;
	private double[] components = new double[3]; // attribute

	// constructor
	public Force(double r1, double r2, double r3) { // to make a force element

		this.components[0] = r1;
		this.components[1] = r2;
		this.components[2] = r3;

	}

	// getComponent method
	public double getComponent(int c) {
		return this.components[c];

	}

	// print method
	public void print() {
		System.out.println(ArrayFormat.format(this.components));
	}

	// print2 method (for structure print)
	public void print2(int k) { // this help us to have a well-shaped print structure
		System.out.println(k + ArrayFormat.format(this.components));
	}

}