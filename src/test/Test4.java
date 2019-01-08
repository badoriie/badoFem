package test;

import fem.*;
//import inf.text.ArrayFormat;
import models.SmallTetraeder;

public class Test4 {

	public static void main(String[] args) {
		Structure struct =SmallTetraeder.createStructure();
		//solve
		struct.solve();
		
		struct.printStructure();
		System.out.println("");
		struct.printResults();
		
	}
}
