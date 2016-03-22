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
	private Favourites faves;
	private SearchFrame search;

	public MenuFrame(SearchFrame searchFrame, Favourites favesFrame){
		super("Amnity Police");

		search = searchFrame;
		faves = favesFrame;

		createWidgets();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void createWidgets(){
		
		JLabel picture = new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg")));
		JButton jbSearch = new JButton("Search");
		JButton jbFavourites = new JButton("Favourites");
		setLayout(new BorderLayout());
		JPanel bottomButtons = new JPanel(new GridLayout(3, 0));
		JPanel spaceborder = new JPanel(new BorderLayout());
		add(spaceborder);
		spaceborder.add(bottomButtons, BorderLayout.SOUTH);
		bottomButtons.add(jbSearch);
		bottomButtons.add(jbFavourites);
		spaceborder.add(picture, BorderLayout.CENTER);
		spaceborder.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		JButton jbAddLoginUser = new JButton("Login Or Create a User");
		bottomButtons.add(jbAddLoginUser);
		
	
		jbSearch.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				//SearchFrame newSearch = new SearchFrame();
				setVisible(true);
				search.setVisible(true);
				
			}
			
			
		});
		jbFavourites.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent a){
				//Favourites newfavourites = new Favourites();
				setVisible(true);
				faves.setVisible(true);
				
			
				
			}
		});
		jbAddLoginUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				LoginCreateFrame loginCreate = new LoginCreateFrame(search, faves);
				loginCreate.setVisible(true);
			}
		});
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
	
	}

	public Favourites getFavouritesFrame()
	{
		return faves;
	}

	/*
	public static void main(String[] args){
		MenuFrame f = new MenuFrame();
	}*/
}
