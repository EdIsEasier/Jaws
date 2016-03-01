package Jaws.Model;

import api.jaws.Ping;

public class comparableShark extends Ping implements Comparable{

	public comparableShark(String n, String t) {
		super(n, t);
	}
	
	
	public int compareTo(Object o) {
		return this.getTime().compareTo(((Ping) o).getTime());
	}
}
