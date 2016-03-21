package TestJaws;

import api.jaws.Jaws;
import api.jaws.Ping;

public class JawsTester {
	public static void main(String[] args){
		Jaws testJaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
		System.out.print(testJaws.past24Hours());
		System.out.print("\n");
		System.out.print(testJaws.pastWeek());
		System.out.print("\n");
		System.out.print(testJaws.pastMonth());
	}
	
}
