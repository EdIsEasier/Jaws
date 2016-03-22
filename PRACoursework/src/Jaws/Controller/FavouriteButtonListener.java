package Jaws.Controller;

import Jaws.View.Favourites;
import api.jaws.Shark;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			faves.addShark(shark);
		}
	}
}
