package fem;

import java.io.Serializable;

import inf.jlinalg.*;
import inf.text.ArrayFormat;;

public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
	// attributes
	private int[] dofNumbers = new int[3]; // each node has 3 DOFs
	private Vector3D position, displacement; // each node has a position and displacement
	private Force force = new Force(0, 0, 0); // initial force for a node
	private Constraint constraint = new Constraint(true, true, true); // initial constraint for a node

	// constructor
	public Node(double x1, double x2, double x3) { // to define a node

		this.position = new Vector3D(x1, x2, x3);
	}

	// setConstraint method
	public void setConstraint(Constraint c) { // to set a constraint on a node
		this.constraint = c;
	}

	// getConstraint method
	public Constraint getConstraint() { // returns the constraint status of the node
		return this.constraint;
	}

	// setForce method
	public void setForce(Force f) { // set force on the node
		this.force = f;
	}

	// getForce method
	public Force getForce() { // returns the force status of the node
		return this.force;
	}

	// enumerateDOFs method
	public int enumerateDOFs(int start) { // enumerate the DOF of a node , it will be needed for assembly part

		for (int i = 0; i <= 2; i++) {

			if (constraint.isFree(i) == false) {
				this.dofNumbers[i] = -1;
			}

			if (constraint.isFree(i) == true) {
				this.dofNumbers[i] = start;
				start++;
			}
		}

		return start;
	}

	// getDOFNumbers method
	public int[] getDOFNumbers() { // returns the vector of DOFs of the node
		return this.dofNumbers;
	}

	// getPosition method
	public Vector3D getPosition() { // return the position of the node

		return position;

	}

	// setDisplacement method
	public void setDisplacement(double[] u) { // sets the displacement on the node
		displacement = new Vector3D(u);

	}

	// getDisplacement method
	public Vector3D getDisplacement() { // returns the displacement of the node
		return displacement;
	}

	// print method
	public void print() { // prints the position of the node

		System.out.println(ArrayFormat.format(this.position.toArray()));
	}

}
