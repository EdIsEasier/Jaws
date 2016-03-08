package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
		JPanel spaceborder = new JPanel(new BorderLayout());
		add(spaceborder);
		spaceborder.add(bottomButtons, BorderLayout.SOUTH);
		bottomButtons.add(jbSearch);
		bottomButtons.add(jbFavourites);
		spaceborder.add(picture, BorderLayout.CENTER);
		spaceborder.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

		
	
		jbSearch.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				SearchFrame newSearch = new SearchFrame();
				setVisible(true);
				newSearch.setVisible(true);
				
				
				
			}
			
			
		});
		jbFavourites.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent a){
				Favourites newfavourites = new Favourites();
				setVisible(true);
				newfavourites.setVisible(true);
				setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			
				
			}
		});
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
	
	}
	
	public static void main(String[] args){
		MenuFrame f = new MenuFrame();
	}
}
