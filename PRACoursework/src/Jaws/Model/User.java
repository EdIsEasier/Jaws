package Jaws.Model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Jaws.View.Favourites;
import api.jaws.Shark;

public class User {
	ArrayList<File> allUsers;
	Favourites faves;
	
	public User(Favourites faves) {
		allUsers = changeToList(addAlreadyCreatedUsers());
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
			try {
				user.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			allUsers.add(user);
			faves.switchUser(user);
		}
	}

	public boolean login(String username){
		for(File f: allUsers){
			if(f.getName().equals(username + ".txt")){
				faves.switchUser(f);
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
	
	public ArrayList<File> changeToList(File[] files){
		ArrayList<File> tempFiles = new ArrayList<File>();
		for(int i = 0; i < files.length; i++){
			tempFiles.add(files[i]);
		}
		return tempFiles;
	}
	
	public File[] addAlreadyCreatedUsers(){
		File path = new File("C:\\Users\\Michael\\git\\pracoursework\\PRACoursework\\Users");
		return path.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename){ 
				return filename.endsWith(".txt");
			}
		} );
	}
}