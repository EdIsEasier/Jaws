package Jaws.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Jaws.View.Favourites;
import api.jaws.Shark;

public class User {
	ArrayList<File> allUsers;
	Favourites faves;
	
	public User(Favourites faves) {
		allUsers = new ArrayList<File>();
		this.faves = faves;
	}
	
	public boolean isUsers(File user){
		if(allUsers.size() > 0){
			for(File f: allUsers){
				if(f.getName().equals(user.getName())){
					return true;
				}
			}
		}
		return false;
	}

	public void addUsers(File user){
		if(isUsers(user)){
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "Username Already Taken", "Warning", warning.INFORMATION_MESSAGE);
		}
		else{
			allUsers.add(user);
			faves.switchUser(user);
		}
	}

	public boolean login(String username){
		for(File f: madeUsers){
			if(s.equals(username)){
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
