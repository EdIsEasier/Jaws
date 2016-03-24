package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

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
	private ArrayList<Ping> last24Hours;
	private ArrayList<Ping> lastWeek;
	private ArrayList<Ping> lastMonth;
	private ArrayList<Ping> nonDuplicates;
	private HashMap<String, Component> componentMap;
	
	public SearchFrame(Favourites faves, Jaws jaws){
		super("Search");
		this.faves = faves;
		shark = jaws;
		last24Hours = jaws.past24Hours();
		lastWeek = jaws.pastWeek();
		lastMonth = jaws.pastMonth();
		nonDuplicates = deleteDuplicates();
		componentMap = new HashMap<String, Component>();
		createDescriptions();
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
		JButton jbStatistics = new JButton("Statistics");
		jbStatistics.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
			
		});
		jpTrackerSearch.add(jbSearch, BorderLayout.NORTH);
		jpTrackerSearch.add(new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg"))), BorderLayout.SOUTH);
		jpTrackerSearch.add(jbStatistics, BorderLayout.CENTER);
		String range = (String)cbRange.getSelectedItem();
		String gender = (String)cbGender.getSelectedItem();
		String stage = (String)cbStage.getSelectedItem();
		String tag = (String)cbTag.getSelectedItem();
		
		
		jbSearch.addActionListener(new SearchListener(this, shark, cbRange, cbGender, cbStage, cbTag, last24Hours, lastWeek, lastMonth, nonDuplicates));
		jpAllDetails = new JPanel(new GridLayout(0, 1));
		JScrollPane jsP = new JScrollPane(jpAllDetails);
		jpAllDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		overallFrame.add(jsP, BorderLayout.CENTER);
		
		add(overallFrame, BorderLayout.CENTER);
		add(new JLabel(shark.getAcknowledgement()), BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void createDescriptions(){
		for(Ping p: nonDuplicates){
			ResultsPanel result = new ResultsPanel(shark.getShark(p.getName()), p, faves);
			componentMap.put(p.getName(), result);
		}
	}
	
	public void putDescription(String sharkName){
		ResultsPanel thePanel = (ResultsPanel) componentMap.get(sharkName);
		jpAllDetails.add(thePanel);
	}

	public void switchToFollowing(String sharkName){
		ResultsPanel thePanel = (ResultsPanel) componentMap.get(sharkName);
		thePanel.getFollowed().setText("Following");
	}
	
	public JPanel getJpAllDetails()
	{
		return jpAllDetails;
	}

	public void clearJpAllDetails()
	{
		jpAllDetails.removeAll();
	}

	public Favourites getFaves()
	{
		return faves;
	}
	
	private ArrayList<Ping> deleteDuplicates(){
		ArrayList<Ping> tempPings = new ArrayList<Ping>();
		ArrayList<Ping> tempPings2 = new ArrayList<Ping>();
		tempPings.addAll(shark.pastMonth());
		tempPings2.addAll(shark.pastMonth());
		ListIterator<Ping> it = tempPings.listIterator(0);
		while(it.hasNext()){
			Ping tempPing = it.next();
			ListIterator<Ping> it2 = tempPings2.listIterator(0);
			while(it2.hasNext()){
				Ping tempPing2 = it2.next();
				if(tempPing.getName().equals(tempPing2.getName())){
					if(changePingToDate(tempPing2).before(changePingToDate(tempPing))){
						it2.remove();
					}
				}
			}
		}
		return tempPings2;
	}
	
	private Calendar changePingToDate(Ping ping){
		Calendar calendar = new GregorianCalendar();
		String time = ping.getTime();
		String[] dates = time.split(" ");
		String[] sDate = dates[0].split("-");
		String[] sTime = dates[1].split(":");
		int[] date = new int[3];
		int[] iTime = new int[3];
		for(int i = 0; i < 3; i++){
			date[i] = Integer.parseInt(sDate[i]);
			iTime[i] = Integer.parseInt(sTime[i]);
		}
		calendar.set(date[0], date[1], date[2], iTime[0], iTime[1], iTime[2]);
		return calendar;
	}
}
