package Jaws.Model;

import java.util.ArrayList;
import java.util.List;

import api.jaws.Shark;

public class User {
	private String name;
	private ArrayList<Shark> followedSharks;
	
	public User(String cust) {
		name = cust;
		followedSharks = new ArrayList<Shark>();
	}
	
	public void addShark(Shark shark){
		followedSharks.add(shark);
	}
	
	public ArrayList<Shark> getSharks(){
		return followedSharks;
	}
	
	public String getName(){
		return name;
	}

}
