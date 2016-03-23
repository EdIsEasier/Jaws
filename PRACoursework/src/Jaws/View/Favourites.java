package Jaws.View;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
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

	private final static Location KINGS_LOCATION = new Location(51.5119, 0.1161);
	private JTextArea jtaSharks;
	private User loggedIn;
	private JList jlSharks;
	private Jaws jaws;
	//private List<Shark> favouriteSharks;
	private DefaultListModel<Shark> favouriteSharksModel;

	public Favourites(Jaws jaws){
		super("Favourites");

		//favouriteSharks = new ArrayList<>();
		favouriteSharksModel = new DefaultListModel<>();
		this.jaws = jaws;
		loggedIn = null;
		addUserFavourites();
		createWidgets();
	}

	private void createWidgets() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel jlText = new JLabel("Your favourite sharks are this far away from you : ");
		add(jlText,BorderLayout.NORTH);
		jtaSharks = new JTextArea();
		jtaSharks.setEditable(false);
		jlSharks = new JList(favouriteSharksModel);
		jlSharks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlSharks.setCellRenderer(new FavouriteSharkCellRenderer());
		//add(jtaSharks, BorderLayout.CENTER);
		add(jlSharks, BorderLayout.CENTER);
		
		pack();
	}

	public void addShark(Shark shark)
	{
		String sharkName = shark.getName();
		Location sharkLocation = jaws.getLastLocation(sharkName);
		double sharkDistance = calculateDistance(KINGS_LOCATION.getLatitude(), KINGS_LOCATION.getLongitude(), sharkLocation.getLatitude(), sharkLocation.getLongitude());
		String strSharkDistance = Double.toString(sharkDistance);
		String sentence = sharkName + " " + strSharkDistance + " meters";
		favouriteSharksModel.addElement(shark);
		jtaSharks.append(sharkName + " " + strSharkDistance + " meters");
		pack();
	}
	
	public void addUserFavourites(){
		if(loggedIn != null){
			for(Shark s: loggedIn.getSharks()){
				addShark(s);
			}
		}
	}

	private double calculateDistance(double lat1, double long1, double lat2, double long2) {
		double earthRadius = 6371000; // in meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(long2 - long1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (earthRadius * c);
	}

	public void switchUser(User user){
		loggedIn = user;
	}
	
	public User getUser(){
		return loggedIn;
	}
}
