package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Jaws.Controller.SearchListener;
import Jaws.Model.User;
import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class SearchFrame extends JFrame
{
	private JTextArea jtaDescription;
	//will probably pass in instead, just using for acknowledgement
	private Jaws shark;
	private JPanel jpAllDetails;
	private Favourites faves;
	private ArrayList<User> users;
	private ArrayList<String> madeUsers;
	private File[] createdUsers;
	private User loggedIn;

	public SearchFrame(Favourites faves, Jaws jaws){
		super("Search");
		this.faves = faves;
		shark = jaws;
		users = new ArrayList<User>();
		createdUsers = addAlreadyCreatedUsers();
		madeUsers = changeFileToString();
		loggedIn = null;
		createWidgets();
	}

	private void createWidgets() {
		setLayout(new BorderLayout());
		JPanel overallFrame = new JPanel(new BorderLayout());
		JPanel jpTracker = new JPanel();
		jpTracker.setLayout(new BoxLayout(jpTracker, BoxLayout.Y_AXIS));
		jpTracker.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPanel jpTrackerRange = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerGender = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerStage = new JPanel(new GridLayout (2, 1));
		JPanel jpTrackerTag = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerSearch = new JPanel(new BorderLayout());
		
		jpTrackerRange.setBorder(BorderFactory.createEtchedBorder());
		jpTrackerGender.setBorder(BorderFactory.createEtchedBorder());
		jpTrackerStage.setBorder(BorderFactory.createEtchedBorder());
		jpTrackerTag.setBorder(BorderFactory.createEtchedBorder());
		jpTrackerSearch.setBorder(BorderFactory.createEtchedBorder());
		
		overallFrame.add(jpTracker, BorderLayout.WEST);
		jpTracker.add(jpTrackerRange);
		jpTracker.add(jpTrackerGender);
		jpTracker.add(jpTrackerStage);
		jpTracker.add(jpTrackerTag);
		jpTracker.add(jpTrackerSearch);
		
		jpTrackerRange.add(new JLabel("Tracking Range"));
		jpTrackerGender.add(new JLabel("Gender"));
		jpTrackerStage.add(new JLabel("Stage of Life"));
		jpTrackerTag.add(new JLabel("Tag Location"));
		
		JComboBox cbRange = new JComboBox();
		JComboBox cbGender = new JComboBox();
		JComboBox cbStage = new JComboBox();
		JComboBox cbTag = new JComboBox();
		
		cbRange.addItem("Last 24 Hours");
		cbRange.addItem("Last Week");
		cbRange.addItem("Last Month");
		
		cbGender.addItem("All");
		cbGender.addItem("Male");
		cbGender.addItem("Female");
		
		cbStage.addItem("All");
		cbStage.addItem("Mature");
		cbStage.addItem("Immature");
		cbStage.addItem("Undetermined");
		
		cbTag.addItem("All");
		for(String c: shark.getTagLocations()){
			cbTag.addItem(c);
		}
		
		jpTrackerRange.add(cbRange);
		jpTrackerGender.add(cbGender);
		jpTrackerStage.add(cbStage);
		jpTrackerTag.add(cbTag);
		
		JButton jbSearch = new JButton("Search");
		jpTrackerSearch.add(jbSearch, BorderLayout.NORTH);
		jpTrackerSearch.add(new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg"))), BorderLayout.CENTER);
		
		String range = (String)cbRange.getSelectedItem();
		String gender = (String)cbGender.getSelectedItem();
		String stage = (String)cbStage.getSelectedItem();
		String tag = (String)cbTag.getSelectedItem();
		
		
		jbSearch.addActionListener(new SearchListener(this, shark, cbRange, cbGender, cbStage, cbTag));
		jpAllDetails = new JPanel(new GridLayout(0, 1));
		JScrollPane jsP = new JScrollPane(jpAllDetails);
		jpAllDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		overallFrame.add(jsP, BorderLayout.CENTER);
		
		add(overallFrame, BorderLayout.CENTER);
		add(new JLabel(shark.getAcknowledgement()), BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public File[] addAlreadyCreatedUsers(){
		File path = new File("C:\\Users\\Michael\\git\\pracoursework\\PRACoursework\\Users");
		return path.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename){ 
				return filename.endsWith(".txt");
			}
		} );
	}
	
	public ArrayList<String> changeFileToString(){
		ArrayList<String> tempUsers = new ArrayList<String>();
		for(File f: createdUsers){
			String getUser = f.getName().substring(f.getName().length(), f.getName().length() - 3);
			tempUsers.add(getUser);
		}
		return tempUsers;
	}
	
	public void createDescriptions(Shark shark, Ping ping){
			ResultsPanel result = new ResultsPanel(shark, ping, faves);
			jpAllDetails.add(result);
	}

	public JPanel getJpAllDetails()
	{
		return jpAllDetails;
	}
}
