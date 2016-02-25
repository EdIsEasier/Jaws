package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		JLabel picture = new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg")));
		JButton jbSearch = new JButton("Search");
		JButton jbFavourites = new JButton("Favourites");
		setLayout(new BorderLayout());
		JPanel bottomButtons = new JPanel(new GridLayout(2, 0));
		add(bottomButtons, BorderLayout.SOUTH);
		bottomButtons.add(jbSearch);
		bottomButtons.add(jbFavourites);
		add(picture, BorderLayout.CENTER);
		
		jbSearch.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				SearchFrame newSearch = new SearchFrame();
				setVisible(false);
				newSearch.setVisible(true);
				
			}
			
			
		});
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		MenuFrame f = new MenuFrame();
	}
}
