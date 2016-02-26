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

public class Favourites extends JFrame{
		
	JLabel favourite1 = new JLabel();
	JButton jbfav = new JButton("Favourite");
	
	public Favourites(){
		super("Favourites");
		
		createWidgets();
	}

	private void createWidgets() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jlText = new JLabel("Your Favourite sharks are this far away from you : ");
		add(jlText,BorderLayout.NORTH);
		JTextArea jtaSharks = new JTextArea("\n\n\n");
		jtaSharks.setEditable(false);
		add(jtaSharks, BorderLayout.CENTER);
		
		
		pack();
	}
	
	public static void main (String [] args){
		Favourites fav  = new Favourites();
		fav.setVisible(true);
	}
	

}
