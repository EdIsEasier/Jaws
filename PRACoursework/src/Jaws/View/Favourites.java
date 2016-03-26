package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Jaws.Controller.FavouriteSharkCellRenderer;
import api.jaws.Jaws;
import api.jaws.Location;

/**
 * Favourites class is a frame for the favourite sharks
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class Favourites extends JFrame{

	private File loggedIn;
	private JList jlSharks;
	private JButton jbMap;
	private Jaws jaws;
	private SearchFrame search;
	private DefaultListModel<String> favouriteSharksModel;
	private String path;
	private JPanel jpRightPanel;
	private boolean sharknado;
	private JTextArea warning;

	/**
	 * Constructor takes our Jaws API and initialises all of our fields, sets the path to the users folder
	 *
	 * @param jaws the jaws api
	 */
	public Favourites(Jaws jaws){
		super("Favourites");
		path = System.getProperty("user.dir") + "\\Users\\";	//path to the Users folder
		favouriteSharksModel = new DefaultListModel<>();	//Holding the favourite sharks on the fram
		warning = new JTextArea();
		sharknado = false;
		this.jaws = jaws;	//initialise our jaws API
		loggedIn = new File(path + "Default.txt");	//initilaise the user to the default user
		addComponentListener(new sharkListListener());
		createWidgets();
	}

	/**
	 * creates all of the components and widgets used in our JFrame,  also used to add Action Listeners where needed
	 *
	 * @see MapFrame
	 */
	private void createWidgets() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel jlText = new JLabel("Your favourite sharks are this far away from you : ");
		jpRightPanel = new JPanel(new BorderLayout());
		jbMap = new JButton("Show on Map");
		jpRightPanel.add(jbMap, BorderLayout.NORTH);
		jbMap.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] sharksArr = new String[favouriteSharksModel.toArray().length]; // array to hold all the shark names from the list
				System.arraycopy(favouriteSharksModel.toArray(), 0, sharksArr, 0, favouriteSharksModel.toArray().length); // copy all the sharks from the list model to the array, because the model returns Object[]
				ArrayList<String> sharks = new ArrayList<String>(Arrays.asList(sharksArr)); // copy the shark names to a new arary list
				HashMap<String, Location> sharksLocs = new HashMap<String, Location>(); // hash map of shark names and their locations

				for(String s : sharks) // for all the favourited sharks
					sharksLocs.put(s, jaws.getLastLocation(s)); // add the shark name and its location to the hash map

				MapFrame map = new MapFrame(Favourites.this); // create a map frame
				map.showOnMap(sharksLocs); // pass in the shark and location hash map which will be used to show the sharks on a map
				map.setVisible(true);
			}
		});

		jlSharks = new JList(favouriteSharksModel);		//creating JList from the defaultlistmodel of favourite sharks
		jlSharks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		//can only select one thing from the lsit at a time
		jlSharks.setCellRenderer(new FavouriteSharkCellRenderer(jaws));		//setting the cell rendered to a new created cell renderer that allows you to add the shark name and the location it is away from kings
		jlSharks.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)		//when the JList changes
			{
				if(isActive()){
					String selectedShark = jlSharks.getSelectedValue().toString();		//get the selected shark
					search.clearJpAllDetails();		//clear all of the original results panels from the search frame
					search.putDescription(selectedShark);		//add the results panel for the selected shark
					search.setVisible(true);		//show the search frame
					search.toFront();		//bring the search frame to the front
				}
			}
		});

		add(jpRightPanel, BorderLayout.EAST);
		add(jlText,BorderLayout.NORTH);
		add(jlSharks, BorderLayout.CENTER);
		
		pack();
	}

	/**
	 * adds the name of the shark that has been followed to the defaultlistmodel
	 *
	 * @param sharkName
	 */
	public void addShark(String sharkName)
	{
		favouriteSharksModel.addElement(sharkName);
		pack();
	}
	
	/**
	 * will populate the favourites tab with all of the sharks that have been followed by the logged in user
	 * 
	 * @see SearchFrame
	 */
	public void addUserFavourites(){
		if(loggedIn != null){	//if there is someone logged in
			try {
				String currentShark;
				BufferedReader reader = new BufferedReader(new FileReader(loggedIn));	//create a filereader to read the logged in users file
				while((currentShark = reader.readLine()) != null){	//whilst there are still sharks to read
					addShark(currentShark);		//add the shark to the defaultlistmodel
					search.switchToFollowing(currentShark);		//change the follow button on the results panel to "following"
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * will unfollow the shark, removes the shark from the defaultlistmodel
	 *
	 * @param sharkName the name of the shark
	 * @see SearchFrame
	 */
	public void unfollowShark(String sharkName)
	{
		favouriteSharksModel.removeElement(sharkName);	//removes shark from defaultlistmodel
		search.switchToFollow(sharkName);	//change the following button on the results panel to "Follow"
	}

	/**
	 * switches the user to the new given user
	 *
	 * @param user the user you want to log in
	 * @see SearchFrame
	 */
	public void switchUser(File user){
		try {
			String currentShark;
			BufferedReader reader = new BufferedReader(new FileReader(loggedIn));
			while((currentShark = reader.readLine()) != null){
				search.switchToFollow(currentShark);		//goes through and switches all the results panels for the sharks that the last user
															//was following back to "Follow"
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loggedIn = user;		//sets the logged in user to the new user
		favouriteSharksModel.clear();	//clears the defaultlistmodel of all followed sharks
		addUserFavourites();	//adds the users favourites sharks back to the frame
		
	}
	
	/**
	 * returns the currently logged in user
	 *
	 * @return loggedIn the current logged in user
	 */
	public File getUser(){
		return loggedIn;
	}

	/**
	 * sets search to the SearchFrame given
	 *
	 * @param search searchframe to update
	 * @see SearchFrame
	 */
	public void setSearchFrame(SearchFrame search)
	{
		this.search = search;
	}

	/**
	 * goes through the list of sharknado sharks and prints their names to the frame
	 *
	 * @param sharkNames list of shark names
	 */
	public void sharknadoWarning(List<String> sharkNames)
	{
		if (!sharknado)
		{
			warning = new JTextArea();			//setting up the warning text area
			warning.setForeground(Color.RED);
			warning.setEditable(false);
			warning.setBackground(this.getBackground());
			warning.setLineWrap(true);
			warning.setWrapStyleWord(true);
			String strWarning = "Sharknado! ";

			if(sharkNames.size() > 1)
			{
				for(String s : sharkNames)
				{
					strWarning += s + ", ";		//adds to the warning the shark names seperated by commas
				}

				strWarning += " are on land!";		//plural
			}
			else
			{
				for(String s : sharkNames)
				{
					strWarning += s;		//if only one then add the shark name
				}

				strWarning += " is on land!";		//singular
			}

			warning.setText(strWarning);		//set the text of the textarea, add it to the frame
			jpRightPanel.add(warning, BorderLayout.CENTER);
			pack();
			sharknado = true;		//there is a sharknado happening
		}
	}

	/**
	 * removes the sharknado warning and sets the sharknado event to false
	 */
	public void removeSharknadoWarning()
	{
		if (warning != null)
		{
			jpRightPanel.remove(warning);
			sharknado = false;
		}
	}

	/**
	 * if there are any favourited sharks then this will allow the user to show the map of where the sharks are located
	 */
	private class sharkListListener implements ComponentListener
	{

		@Override
		public void componentShown(ComponentEvent e)	//when the frame is shown do this
		{
			if(jlSharks.getModel().getSize() != 0)	//if there are any sharks in the favourites
				jbMap.setEnabled(true);		//enable the map button
			else							//if not
				jbMap.setEnabled(false);	//disable it
		}

		@Override
		public void componentResized(ComponentEvent e)
		{}

		@Override
		public void componentMoved(ComponentEvent e)
		{}

		@Override
		public void componentHidden(ComponentEvent e)
		{}
	}
}
