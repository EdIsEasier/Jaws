package Jaws.Controller;

import Jaws.View.Favourites;
import api.jaws.Shark;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

/**
 * FavouriteButtonClass is an action listener used to add
 * and remove favourite sharks
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class FavouriteButtonListener implements ActionListener
{
	private Favourites faves; // reference to the Favourites window
	private Shark shark; // shark to be added to or removed from favourites
	private String path; // path to Users folder

	/**
	 * Constructor that initialises the Favourites window reference,
	 * shark and path
	 *
	 * @param faves reference to the Favourites window
	 * @param shark shark to be added to or removed from favourites
	 */
	public FavouriteButtonListener(Favourites faves, Shark shark)
	{
		this.faves = faves;
		this.shark = shark;
		path = System.getProperty("user.dir") + "\\Users\\";
	}

	/**
	 * the button press for the follow button, if the shark is not being followed then follow the shark, if it is then unfollow it
	 * 
	 * @param ActionEvent the button press
	 * @see Favourites
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton favButton = (JButton)e.getSource(); // get the Follow button
		if (!favButton.getText().equals("Following")) // if the shark isn't being followed
		{
			favButton.setText("Following"); // change the button to say "Following"
			faves.addShark(shark.getName()); // add the shark to the Favourites window
			writeToFile(); // add the shark to the current user's file
		}
		else // if the shark is being followed
		{
			favButton.setText("Follow"); // change the button to say "Follow"
			faves.unfollowShark(shark.getName()); // remove the shark from the Favourites window
			removeFromFile(); // remove the shark from file
		}
	}

	/**
	 * Adds current shark to the current user's file
	 */
	public void writeToFile(){
		File user = faves.getUser(); // get the current user
		try{
			PrintWriter writer = new PrintWriter(new FileWriter(user, true)); // start a PrintWriter
			writer.append(shark.getName() + "\r\n"); // append the current shark's name to the file
			writer.close(); // close the writer
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Removes current shark from the current user's file
	 */
	public void removeFromFile()
	{
		File user = faves.getUser(); // get the current user
		File tempUser = new File(path + "temp" + user.getName()); // create a temporary file called temp+User

		try{
			BufferedReader reader = new BufferedReader(new FileReader(user)); // start a BufferedReader to read the current user's file
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempUser)); // start a BufferedWriter to write to the temp file

			String toRemove = shark.getName(); // get the current sharl's name
			String currentLine; // most recently read line from file

			while((currentLine = reader.readLine()) != null) { // while there is still a line to read
				String trimmedLine = currentLine.trim(); // remove any potential whitespace from line
				if(trimmedLine.equals(toRemove)) continue; // if the current shark name is the one we want to remove, skip it
				writer.write(currentLine + System.getProperty("line.separator")); // if it's any other name, add it to the temporary file
			}

			writer.close(); // close the writer and reader
			reader.close();

			Files.delete(user.toPath()); // delete the original user file
			Files.move(tempUser.toPath(), user.toPath()); // rename the temp file after the user
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
