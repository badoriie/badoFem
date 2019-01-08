package fem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import inf.jlinalg.*;
import inf.jlinalg.IMatrix;
import inf.jlinalg.lse.GeneralMatrixLSESolver;
import inf.jlinalg.lse.ILSESolver;
import inf.text.ArrayFormat;

public class Structure {
	// attributes
	private ArrayList<Node> nodes = new ArrayList<Node>(); // data structure of all nodes
	private ArrayList<Element> elements = new ArrayList<Element>(); // data structures of all elements

	// addNode method
	public Node addNode(double x1, double x2, double x3) { // in order to add a node to the main structure
		Node nt = new Node(x1, x2, x3);
		nodes.add(nt);
		return nt;
	}

	// deleteNode method
	public void deleteNode(int id) {

		nodes.remove(id);
	}

	// getNumberOfNodes method
	public int getNumberOfNodes() { // returns the number of all nodes in the main structure
		return nodes.size();
	}

	// getNode methods
	public Node getNode(int id) { // returns a node by the id
		return nodes.get(id);
	}

	// addElement method
	public Element addElement(double e, double a, int n1, int n2) { // add an element to the main structure
		Element et = new Element(e, a, this.getNode(n1), this.getNode(n2));
		elements.add(et);
		return et;
	}

	// deleteElement method
	public void deleteElement(int id) {

		elements.remove(id);

	}

	// getNumberOfElements method
	public int getNumberOfElements() { // returns the number of all elements in the main structure
		return elements.size();
	}

	// getElement method
	public Element getElement(int id) { // returns a element by the id
		return elements.get(id);
	}

	// printStructure method
	public void printStructure() { // print the whole structure in a well-organized style

		System.out.println("Listing Structure");
		System.out.println("");

		System.out.println("Nodes");
		String[] header1 = { "idx	", "x1		", "x2		", "x3" };
		System.out.println(ArrayFormat.format(header1));
		for (int i = 0; i < getNumberOfNodes(); i++) {
			double[] nodePosition = getNode(i).getPosition().toArray();
			System.out.println(i + ArrayFormat.format(nodePosition));
		}
		System.out.println("");

		System.out.println("Constraints");
		String[] header2 = { "node	", "u1	", "u2	", "u3" };
		System.out.println(ArrayFormat.format(header2));
		for (int i = 0; i < getNumberOfNodes(); i++) {
			getNode(i).getConstraint().print2(i);
			;

		}

		System.out.println("");

		System.out.println("Forces");
		String[] header3 = { "node	", "r1		", "r2		", "r3" };
		System.out.println(ArrayFormat.format(header3));
		for (int i = 0; i < getNumberOfNodes(); i++) {
			getNode(i).getForce().print2(i);

		}

		System.out.println("");

		System.out.println("Elements");
		String[] header4 = { "idx	", "E		", "A	     ", "Length" };
		System.out.println(ArrayFormat.format(header4));
		for (int i = 0; i < getNumberOfElements(); i++) {
			getElement(i).print2(i);

		}
	}

	// enumerateDOFs method
	private int enumerateDOFs() { // it is needed for assembly part
		int eqn = 0;
		for (int i = 0; i < getNumberOfNodes(); i++) {
			eqn = getNode(i).enumerateDOFs(eqn);
		}
		for (int i = 0; i < getNumberOfElements(); i++) {
			getElement(i).enumerateDOFs();
		}

		return eqn;
	}

	// solver method
	public void solve() { // solves the linear system of equations

		// create the solver object
		ILSESolver solver = new GeneralMatrixLSESolver();
		// info object for coefficient matrix
		QuadraticMatrixInfo aInfo = solver.getAInfo();
		// get coefficient matrix
		IMatrix KGlobal = solver.getA();// left side matrix
		// right hand side
		double[] rGlobal = new double[enumerateDOFs()];// right side vector

		// initialize solver
		aInfo.setSize(enumerateDOFs());
		solver.initialize();

		// set entries of matrix and right hand side vector
		assembleStiffnessMatrix(KGlobal);
		assembleLoadVector(rGlobal);

		// after calling solve, global load vector contains the solution
		try {
			solver.solve(rGlobal);
		} catch (SolveFailedException e) {
			System.out.println("Solve failed: " + e.getMessage());
		}

		selectDisplacement(rGlobal);
	}

	// assembleLoadVector method
	private void assembleLoadVector(double[] rGlobal) { // to obtain the global load vector

		for (int k = 0; k < getNumberOfNodes(); k++) {

			for (int i = 0; i < 3; i++) {
				if (getNode(k).getDOFNumbers()[i] != -1) {

					rGlobal[getNode(k).getDOFNumbers()[i]] = getNode(k).getForce().getComponent(i);

				}
			}

		}

	}

	// assembleAtiffnessMatrix method
	private void assembleStiffnessMatrix(IMatrix KGlobal) { // to obtain the global stiffness matrix

		for (int k = 0; k < getNumberOfElements(); k++) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {

					if (getElement(k).getDOFNumbers()[i] != -1 && getElement(k).getDOFNumbers()[j] != -1) {

						KGlobal.add(getElement(k).getDOFNumbers()[i], getElement(k).getDOFNumbers()[j],
								getElement(k).computeStiffnessMatrix().get(i, j));

					}

				}

			}

		}
	}

	// selectDisplacement method
	private void selectDisplacement(double[] rGlobal) { // it maps the displacement to its corresponding DOF
		double[] uT = new double[3];
		int j = 0;
		for (int k = 0; k < getNumberOfNodes(); k++) {

			for (int i = 0; i < 3; i++) {
				if (getNode(k).getDOFNumbers()[i] != -1) {
					uT[i] = rGlobal[j];

					j++;

				} else {

					uT[i] = 0;
				}

			}

			getNode(k).setDisplacement(uT);

		}
	}

	// printResults method
	public void printResults() { // printing the results of FEM

		System.out.println("Listing analysis results");
		System.out.println("");
		System.out.println("Displacements");
		String[] headeru = { "node	", "u1		", "u2		", "u3" };
		System.out.println(ArrayFormat.format(headeru));
		for (int i = 0; i < getNumberOfNodes(); i++) {
			double[] nodeDisplacement = getNode(i).getDisplacement().toArray();
			System.out.println(i + ArrayFormat.format(nodeDisplacement));
		}

		System.out.println("");

		System.out.println("Element forces");
		String[] headerf = { "elem	", "force" };
		System.out.println(ArrayFormat.format(headerf));
		for (int i = 0; i < getNumberOfElements(); i++) {
			double elementForces = getElement(i).computeForce();
			System.out.println(i + ArrayFormat.format(elementForces));
		}
	}

	public void save(String filename) {

		try {
			FileOutputStream saveFile = new FileOutputStream(filename);

			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(nodes);
			save.writeObject(elements);

			save.close();
		}

		catch (Exception exc) {

			exc.printStackTrace();

		}
	}

	@SuppressWarnings("unchecked")
	public void load(String filename) {

		try {
			// Open file to read from, named SavedObj.sav.
			FileInputStream loadFile = new FileInputStream(filename);

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream load = new ObjectInputStream(loadFile);

			nodes.clear();
			elements.clear();

			nodes = (ArrayList<Node>) load.readObject();
			elements = (ArrayList<Element>) load.readObject();

			// Close the file.
			load.close(); // This also closes saveFile.
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}

	}

	public void clear() {// clearing the elements and nodes

		nodes.clear();
		elements.clear();

	}

}
