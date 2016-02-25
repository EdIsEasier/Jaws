package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuFrame extends JFrame{
	
	public MenuFrame(){
		super("Amnity Police");
		
		createWidgets();
	}
	
	public void createWidgets(){
		JLabel picture = new JLabel(new ImageIcon(this.getClass().getResource("resources/randypic.jpg")));
		JButton jbSearch = new JButton("Search");
		JButton jbFavourites = new JButton("Favourites");
		setLayout(new BorderLayout());
		JPanel bottomButtons = new JPanel(new GridLayout(2, 0));
		add(bottomButtons, BorderLayout.SOUTH);
		bottomButtons.add(jbSearch);
		bottomButtons.add(jbFavourites);
		add(picture, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		MenuFrame f = new MenuFrame();
	}
}
