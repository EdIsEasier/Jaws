package Jaws.Controller;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FavouriteButtonListener implements ActionListener
{
	public FavouriteButtonListener()
	{

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton favButton = (JButton)e.getSource();
		favButton.setText("Following");
	}
}
