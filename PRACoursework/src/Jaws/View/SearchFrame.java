package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Jaws.Controller.SearchListener;
import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class SearchFrame extends JFrame{
	private JTextArea jtaDescription;
	//will probably pass in instead, just using for acknowledgement
	private Jaws shark = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
	private JPanel jpAllDetails;
	
	public SearchFrame(){
		super("Search");
		
		createWidgets();
	}

	private void createWidgets() {
		setLayout(new BorderLayout());
		JPanel overallFrame = new JPanel(new BorderLayout());
		JPanel jpTracker = new JPanel(new GridLayout(5, 1));
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
		JPanel jpAllDetails = new JPanel(new GridLayout(2, 1));
		jpAllDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//just a test for our ResultsPanel
		//createDescriptions(3, jpAllDetails);
		overallFrame.add(jpAllDetails, BorderLayout.CENTER);
		
		add(overallFrame, BorderLayout.CENTER);
		add(new JLabel(shark.getAcknowledgement()), BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void createDescriptions(Shark shark, Ping ping){
		ResultsPanel result = new ResultsPanel(shark, ping);
		jpAllDetails.add(result);
	}
}
