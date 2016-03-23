package Jaws.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import api.jaws.Shark;

public class User {
	private String name;
	private ArrayList<Shark> followedSharks;
	private File sharks;
	
	public User(String cust) {
		name = cust;
		followedSharks = new ArrayList<Shark>();
		sharks = new File(name + ".txt");
	}
	
	public boolean isUsers(User user){
		if(users.size() > 0){
			for(String s: madeUsers){
				if(s.equals(user.getName())){
					return true;
				}
			}
		}
		return false;
	}

	public void addUsers(User user){
		if(isUsers(user)){
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "Username Already Taken", "Warning", warning.INFORMATION_MESSAGE);
		}
		else{
			users.add(user);
			loggedIn = user;
			faves.switchUser(user);
		}
	}

	public boolean login(String username){
		for(String s: madeUsers){
			if(s.equals(username)){
				loggedIn = ;
				faves.switchUser(u);
				return true;
			}
		}
		return false;
	}

	public void failLogin(String username){
		if(!login(username)){
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "Could Not Find User", "Login Fail", warning.INFORMATION_MESSAGE);
		}
	}

}
