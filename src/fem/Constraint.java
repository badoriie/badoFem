package fem;

import java.io.Serializable;

import inf.text.ArrayFormat;

public class Constraint implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean[] free = new boolean[3]; // attribute

	// constructor
	public Constraint(boolean u1, boolean u2, boolean u3) { // in order to define DOF
		this.free[0] = u1;
		this.free[1] = u2;
		this.free[2] = u3;

	}

	// isFree method
	public boolean isFree(int c) { // to check the DOF
		return this.free[c];
	}

	// print method
	public void print() { // to print the constraint status

		String[] s = new String[3];

		for (int i = 0; i <= 2; i++) {
			if (this.isFree(i) == true) {
				s[i] = " free ";

			} else {
				s[i] = " fixed ";
			}

		}
		System.out.println(ArrayFormat.format(s));

	}

	// print2 method (for structure print)
	public void print2(int k) { // for having well-posed print

		String[] s = new String[3];

		for (int i = 0; i <= 2; i++) {
			if (this.isFree(i) == true) {
				s[i] = " free ";

			} else {
				s[i] = " fixed";
			}

		}
		System.out.println(k + "	" + ArrayFormat.format(s));

	}
}
