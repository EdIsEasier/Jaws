package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Jaws.Model.User;

/**
 * MenuFrame class is the main menu window,
 * the first UI component the user sees when
 * they run the application
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class MenuFrame extends JFrame{
	private Favourites faves; // Reference to the Favourites window
	private SearchFrame search; // Reference to the Search window
	private User user; // Reference to the User class
	private String path; // Path to the Users folder

	/**
	 * Constructor that initialises all the required frame and User class
	 * references so that the Menu frame can communicate with them
	 *
	 * @param user reference to the User class
	 * @param searchFrame reference to the Search window
	 * @param favesFrame reference to the Favourites window
	 */
	public MenuFrame(User user ,SearchFrame searchFrame, Favourites favesFrame){
		super("Amnity Police");
		
		path = System.getProperty("user.dir") + "\\Users\\";
		search = searchFrame;
		faves = favesFrame;
		this.user = user;

		createWidgets();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Initialises and creates all the components for the window
	 */
	private void createWidgets(){
		
		JLabel picture = new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg")));
		JButton jbSearch = new JButton("Search");
		JButton jbFavourites = new JButton("Favourites");
		setLayout(new BorderLayout());
		JPanel bottomButtons = new JPanel(new GridLayout(4, 0));
		JPanel spaceborder = new JPanel(new BorderLayout());
		add(spaceborder);
		spaceborder.add(bottomButtons, BorderLayout.SOUTH);
		bottomButtons.add(jbSearch);
		bottomButtons.add(jbFavourites);
		spaceborder.add(picture, BorderLayout.CENTER);
		spaceborder.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		JButton jbAddLoginUser = new JButton("Login Or Create a User");
		bottomButtons.add(jbAddLoginUser);
		JButton jbLogout = new JButton("LogOut");
		bottomButtons.add(jbLogout);

		jbSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				search.setVisible(true);
			}
		});

		jbFavourites.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				faves.setVisible(true);
			}
		});

		jbAddLoginUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				LoginCreateFrame loginCreate = new LoginCreateFrame(user);
				loginCreate.setVisible(true);
			}
		});

		jbLogout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// if the user logs out, change the curernt user to the Default user
				File defaultUser = new File(path + "Default.txt");
				faves.switchUser(defaultUser);
				faves.removeSharknadoWarning(); // remove Sharknado warning if there is one, so the new user won't see it
			}
		});
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}