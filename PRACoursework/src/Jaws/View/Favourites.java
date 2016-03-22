package Jaws.View;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Jaws.View.SearchFrame;
import api.jaws.Shark;

public class Favourites extends JFrame{
		
	private JTextArea jtaSharks;
	
	public Favourites(){
		super("Favourites");

		createWidgets();
	}

	private void createWidgets() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel jlText = new JLabel("Your Favourite sharks are this far away from you : ");
		add(jlText,BorderLayout.NORTH);
		jtaSharks = new JTextArea();
		jtaSharks.setEditable(false);
		add(jtaSharks, BorderLayout.CENTER);
		
		pack();
	}

	public void addShark(Shark shark)
	{
		jtaSharks.append(shark.getName() + "\n");
		pack();
	}
}
