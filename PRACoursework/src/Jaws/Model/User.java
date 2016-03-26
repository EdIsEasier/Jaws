package Jaws.Model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import Jaws.View.Favourites;

/**
 * User class manages new and existing users.
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class User {
	ArrayList<File> allUsers; // List of all existing users
	Favourites faves; // Favourites window
	String path; // Path to Users folder

	/**
	 * Constructor that initialises the path to where all the users are stored,
	 * gets any existing users and takes a reference to the Favourites window
	 * so it can be updated with the correct user's favourite sharks.
	 *
	 * @param faves reference to the Favourites window
	 */
	public User(Favourites faves) {
		path = System.getProperty("user.dir") + "\\Users";
		allUsers = new ArrayList<>(Arrays.asList(addAlreadyCreatedUsers()));
		this.faves = faves;
	}

	/**
	 * Checks if provided user exists
	 *
	 * @param user user to find
	 * @return boolean true if user found, false otherwise
	 */
	public boolean isUsers(File user){
		if(allUsers.size() > 0){ // if there is at least one user
			for(File f: allUsers){
				if(f.getName().equals(user.getName())){ // if user is found
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a new user
	 *
	 * @param user user to add
	 * @return boolean true if the user was added successfully, false otherwise
	 * @see Favourites
	 */
	public boolean addUsers(File user){
		if(isUsers(user)){ // if the user exists
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "Username Already Taken", "Warning", warning.INFORMATION_MESSAGE);
			return false;
		}
		else{
			try {
				user.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			allUsers.add(user); // add the newly added user to allUsers
			faves.switchUser(user); // switch the user in the Favourites window so that the right favourite sharks are shown
			return true;
		}
	}

	/**
	 * Log into an existing user
	 *
	 * @param username username to log into
	 * @return boolean true if successfully logged in, false otherwise
	 * @see Favourites
	 */
	public boolean login(String username){
		for(File f: allUsers){
			if(f.getName().equals(username + ".txt")){ // if the user file exists
				faves.switchUser(f); // switch the user in the Favourites window so that the right favourite sharks are shown
				faves.removeSharknadoWarning(); // remove Sharknado warning if there is one, so the new user won't see it
				return true;
			}
		}
		return false;
	}

	/**
	 * Shows a warning dialog if login fails
	 *
	 * @param username user to log into
	 * @return boolean true if login failed, false otherwise
	 */
	public boolean failLogin(String username){
		if(!login(username)){ // if login was unsuccessful
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "Could Not Find User", "Login Fail", warning.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * Gets all the existing users in the Users directory
	 *
	 * @return File[] an array of existing user files
	 */
	public File[] addAlreadyCreatedUsers(){
		File newUser = new File(path);
		return newUser.listFiles(new FilenameFilter() {
			// filters all the files that end in .txt
			public boolean accept(File dir, String filename){ 
				return filename.endsWith(".txt");
			}
		} );
	}
}