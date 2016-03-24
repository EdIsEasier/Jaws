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
import java.nio.file.Path;

public class FavouriteButtonListener implements ActionListener
{
	private Favourites faves;
	private Shark shark;
	private String path;

	public FavouriteButtonListener(Favourites faves, Shark shark)
	{
		this.faves = faves;
		this.shark = shark;
		path = System.getProperty("user.dir") + "\\Users\\";
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton favButton = (JButton)e.getSource();
		if (!favButton.getText().equals("Following"))
		{
			favButton.setText("Following");
			faves.addShark(shark.getName());
			writeToFile();
		}
		else
		{
			favButton.setText("Follow");
			faves.unfollowShark(shark.getName());
			removeFromFile();
		}
	}

	public void writeToFile(){
		File user = faves.getUser();
		try{
			PrintWriter writer = new PrintWriter(new FileWriter(user, true));
			writer.append(shark.getName() + "\r\n");
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void removeFromFile()
	{
		File user = faves.getUser();
		File tempUser = new File(path + "temp" + user.getName());

		try{
			BufferedReader reader = new BufferedReader(new FileReader(user));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempUser));

			String toRemove = shark.getName();
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				if(trimmedLine.equals(toRemove)) continue;
				writer.write(currentLine + System.getProperty("line.separator"));
			}

			writer.close();
			reader.close();
			System.out.println(user.getAbsolutePath());
			System.out.println(tempUser.getAbsolutePath());
			Files.delete(user.toPath());
			Files.move(tempUser.toPath(), user.toPath());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
