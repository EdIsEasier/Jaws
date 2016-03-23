package Jaws.View;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import Jaws.Controller.FavouriteSharkCellRenderer;
import api.jaws.Jaws;

public class Favourites extends JFrame{

	private File loggedIn;
	private JList jlSharks;
	private Jaws jaws;
	private DefaultListModel<String> favouriteSharksModel;
	private String path;

	public Favourites(Jaws jaws){
		super("Favourites");
		path = System.getProperty("user.dir") + "\\Users\\";
		//favouriteSharks = new ArrayList<>();
		favouriteSharksModel = new DefaultListModel<>();
		this.jaws = jaws;
		loggedIn = new File(path + "Default.txt");
		addUserFavourites();
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
	
	public void addUserFavourites(){
		if(loggedIn != null){
			try {
				String currentShark;
				BufferedReader reader = new BufferedReader(new FileReader(loggedIn));
				while((currentShark = reader.readLine()) != null){
					addShark(currentShark);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void switchUser(File user){
		loggedIn = user;
		favouriteSharksModel.clear();
		addUserFavourites();
	}
	
	public File getUser(){
		return loggedIn;
	}
}
