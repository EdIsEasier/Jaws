package Jaws.View;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import Jaws.Model.User;
import Jaws.View.SearchFrame;
import Jaws.Controller.FavouriteSharkCellRenderer;
import api.jaws.Location;
import api.jaws.Shark;
import api.jaws.Jaws;

public class Favourites extends JFrame{

	private File loggedIn;
	private JList jlSharks;
	private Jaws jaws;
	private DefaultListModel<String> favouriteSharksModel;

	public Favourites(Jaws jaws){
		super("Favourites");

		//favouriteSharks = new ArrayList<>();
		favouriteSharksModel = new DefaultListModel<>();
		this.jaws = jaws;
		loggedIn = new File("C:\\Users\\Michael\\git\\pracoursework\\PRACoursework\\Users\\Default.txt");
		//addUserFavourites();
		createWidgets();
	}

	private void createWidgets() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel jlText = new JLabel("Your favourite sharks are this far away from you : ");
		add(jlText,BorderLayout.NORTH);
		jlSharks = new JList(favouriteSharksModel);
		jlSharks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlSharks.setCellRenderer(new FavouriteSharkCellRenderer(jaws));
		//add(jtaSharks, BorderLayout.CENTER);
		add(jlSharks, BorderLayout.CENTER);
		
		pack();
	}

	public void addShark(String sharkName)
	{
		favouriteSharksModel.addElement(sharkName);
		pack();
	}
	
	/*public void addUserFavourites(){
		if(loggedIn != null){
			for(Shark s: loggedIn.getSharks()){
				addShark(s);
			}
		}
	}*/

	public void switchUser(File user){
		loggedIn = user;
	}
	
	public File getUser(){
		return loggedIn;
	}
}
