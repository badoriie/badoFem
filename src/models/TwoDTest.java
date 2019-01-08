package models;

import fem.*;
import inf.v3d.view.Viewer;

public class TwoDTest {

public static Structure createStructure() {
		
		Structure struct= new Structure();
		double lb=20.0;
		double a=0.015;//Area
		double e=2.1e11;//Elasticity modulus
		Constraint c1 = new Constraint(false,false,false);
		Constraint c2= new Constraint(true, true, false);
		Force f = new Force(100e3,-50e3,0);
		
		//create nodes
		Node n1=struct.addNode(0.0, 0.0, 0.0);
		Node n2=struct.addNode(lb, 0, 0);
		Node n3=struct.addNode(lb/2, 20, 0);
		
		
		//apply BCs
		n3.setForce(f);
		n1.setConstraint(c1);
		n2.setConstraint(c1);
		n3.setConstraint(c2);
		
		//create elements
		struct.addElement(e, a, 0, 1);
		struct.addElement(e, a, 0, 2);
		struct.addElement(e, a, 1, 2);
		
		//return the new structure
		return struct;
		
	}
public static void main(String[] args) {
	Viewer viewer =new Viewer();
	Structure struct= createStructure();
	Visualizer viz = new Visualizer(struct,viewer);
	struct.solve();
	struct.printStructure();
	struct.printResults();
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
