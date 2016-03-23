package Jaws.Controller;

import Jaws.View.Favourites;
import api.jaws.Shark;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FavouriteButtonListener implements ActionListener
{
	private Favourites faves;
	private Shark shark;

	public FavouriteButtonListener(Favourites faves, Shark shark)
	{
		this.faves = faves;
		this.shark = shark;
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
}
