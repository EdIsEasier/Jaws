package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Jaws.Controller.SearchListener;
import Jaws.Controller.SharkOfDayController;
import api.jaws.Jaws;
import api.jaws.Ping;

/**
 * SearchFrame class is the window where the user will be able to
 * retrieve sharks with their descriptions from the Jaws API
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class SearchFrame extends JFrame
{
	private Jaws shark; // Reference to the Jaws API
	private JPanel jpAllDetails; // the panel on the right of the frame where all the shark descriptions are
	private Favourites faves; // Reference to the Favourites window
	private ArrayList<Ping> last24Hours; // list of pings from the last 24 hours
	private ArrayList<Ping> lastWeek; // list of pings from last week
	private ArrayList<Ping> lastMonth; // list of pings from last month
	private ArrayList<Ping> nonDuplicates; // list of all the pings with duplicates removed
	private HashMap<String, Component> componentMap; // map of shark names and their corresponding description panels
	private CompareView comparison; // reference to the comparison window

	/**
	 * Constructor that initialises the passed in Favourites and API references,
	 * gets all the sharks from the API, deletes all the duplicates
	 * and creates the description panels for all those sharks
	 *
	 * @param faves reference to the Favourites window
	 * @param jaws reference to the Jaws API
	 */
	public SearchFrame(Favourites faves, Jaws jaws){
		super("Search");
		this.faves = faves;
		shark = jaws;
		last24Hours = jaws.past24Hours(); // get all the pings from the last 24 hours
		lastWeek = jaws.pastWeek(); // get all the pings from the last week
		lastMonth = jaws.pastMonth(); // get all the pings from the last month
		nonDuplicates = deleteDuplicates(); // delete any duplicate sharks
		componentMap = new HashMap<String, Component>(); // initialise the map of shark names and their corresponding panels
		comparison = null; // set the comparison view to null
		createDescriptions(); // create the shark descriptions
		createWidgets();
	}

	/**
	 * Initialises and creates all the components for the window
	 */
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
		JButton jbSofDay = new JButton("Shark Of The Day");
		
		jbStatistics.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
			
		});

		// Get the shark picture from the resources folder
		jpTrackerSearch.add(new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg"))), BorderLayout.SOUTH);
		JPanel searchStatsAndSharks = new JPanel(new GridLayout(3, 1));
		searchStatsAndSharks.add(jbSearch);
		searchStatsAndSharks.add(jbStatistics);
		searchStatsAndSharks.add(jbSofDay);
		jpTrackerSearch.add(searchStatsAndSharks, BorderLayout.NORTH);
		
		jbSofDay.addActionListener(new SharkOfDayController(nonDuplicates, shark));

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

	/**
	 * Creates a description panel for each of the sharks and
	 * adds it to the componentMap with the corresponding
	 * shark's name, so it can be accessed later
	 * 
	 * @see ResultsPanel
	 */
	public void createDescriptions(){
		for(Ping p: nonDuplicates){
			ResultsPanel result = new ResultsPanel(this, shark.getShark(p.getName()), p, faves);
			componentMap.put(p.getName(), result);
		}
	}

	/**
	 * Adds the passed in shark's description panel to the panel on the right
	 *
	 * @param sharkName shark's name
	 * @see ResultsPanel
	 */
	public void putDescription(String sharkName){
		ResultsPanel thePanel = (ResultsPanel) componentMap.get(sharkName);
		jpAllDetails.add(thePanel);
	}

	/**
	 * Changes the passed in shark's button to say "Following"
	 *
	 * @param sharkName shark's name
	 */
	public void switchToFollowing(String sharkName){
		ResultsPanel thePanel = (ResultsPanel) componentMap.get(sharkName);
		thePanel.getFollowed().setText("Following");
	}

	/**
	 * Changes the passed in shark's button to say "Follow"
	 *
	 * @param sharkName shark's name
	 * @see ResultsPanel
	 */
	public void switchToFollow(String sharkName){
		ResultsPanel thePanel = (ResultsPanel) componentMap.get(sharkName);
		thePanel.getFollowed().setText("Follow");
	}

	/**
	 * Gets the panel on the right that has all the shark descriptions
	 *
	 * @return JPanel the shark results panel
	 */
	public JPanel getJpAllDetails()
	{
		return jpAllDetails;
	}

	/**
	 * Clears the panel on the right so it has no descriptions
	 */
	public void clearJpAllDetails()
	{
		jpAllDetails.removeAll();
	}

	/**
	 * Removes any duplicate pings, so each shark will only
	 * appear once
	 *
	 * @return ArrayList&lt;Ping&gt; list of sharks without duplicates
	 * @see Ping
	 */
	private ArrayList<Ping> deleteDuplicates(){
		ArrayList<Ping> tempPings = new ArrayList<Ping>();
		ArrayList<Ping> tempPings2 = new ArrayList<Ping>();
		tempPings.addAll(lastMonth); // list of all the pings which will be compared against
		tempPings2.addAll(lastMonth); // second list of all the pings which will be returned
		ListIterator<Ping> it = tempPings.listIterator(0); // first list's iterator
		while(it.hasNext()){ // if there is another ping
			Ping tempPing = it.next(); // get that ping
			ListIterator<Ping> it2 = tempPings2.listIterator(0); // second list's iterator
			while(it2.hasNext()){ // is the second list has another ping
				Ping tempPing2 = it2.next(); // get that ping
				if(tempPing.getName().equals(tempPing2.getName())){ // if the ping from the first list belongs to the same shark
					if(changePingToDate(tempPing2).before(changePingToDate(tempPing))){ // compare their dates
						it2.remove(); // remove the older one
					}
				}
			}
		}
		return tempPings2;
	}

	/**
	 * Changes a ping to a Calendar so their dates and times
	 * can be compared
	 *
	 * @param ping ping to convert
	 * @return Calendar converted ping
	 * @see Ping
	 */
	private Calendar changePingToDate(Ping ping){
		Calendar calendar = new GregorianCalendar(); // create new Gregorian Calendar
		String time = ping.getTime(); // get the ping's time
		String[] dates = time.split(" "); // split the date and time
		String[] sDate = dates[0].split("-"); // split the date
		String[] sTime = dates[1].split(":"); // split the time
		int[] date = new int[3];
		int[] iTime = new int[3];
		for(int i = 0; i < 3; i++){ // put the date and time into their respective arrays
			date[i] = Integer.parseInt(sDate[i]);
			iTime[i] = Integer.parseInt(sTime[i]);
		}
		calendar.set(date[0], date[1], date[2], iTime[0], iTime[1], iTime[2]); // create a calendar with the ping's date and time
		return calendar;
	}

	/**
	 * Gets the comparison view
	 *
	 * @return CompareView comparison
	 * @see CompareView
	 */
	public CompareView getCompare(){
		return comparison;
	}

	/**
	 * Sets the comparison view
	 *
	 * @param compare comparison view
	 * @see CompareView
	 */
	public void setCompare(CompareView compare){
		comparison = compare;
	}
}
