package Jaws.View;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Jaws.Controller.FavouriteSharkCellRenderer;
import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class Favourites extends JFrame{

	private File loggedIn;
	private JList jlSharks;
	private Jaws jaws;
	private SearchFrame search;
	private DefaultListModel<String> favouriteSharksModel;
	private String path;
	private ArrayList<Ping> sharkPings;

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
		jlSharks.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(isActive()){
					String selectedShark = jlSharks.getSelectedValue().toString();
					for(Ping p : sharkPings)
					{
						if(p.getName().equals(selectedShark))
						{
							search.clearJpAllDetails();
							search.createDescriptions(jaws.getShark(selectedShark), p);
							search.setVisible(true);
							search.toFront();
							break;
						}
					}
				}
			}
		});
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

	public void updateSharks(ArrayList<Ping> pings)
	{
		this.sharkPings = pings;
	}

	public void setSearchFrame(SearchFrame search)
	{
		this.search = search;
	}
}
