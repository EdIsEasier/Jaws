package TestJaws;

import api.jaws.Jaws;

public class JawsTester {
	public static void main(String[] args){
		Jaws testJaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
		System.out.println(testJaws.getLastUpdated());
	}
	
}
