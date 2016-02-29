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

import api.jaws.Jaws;

public class SearchFrame extends JFrame{
	private JTextArea jtaDescription;
	//will probably pass in instead, just using for acknowledgement
	private Jaws shark = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
	
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
		
		cbGender.addItem("Male");
		cbGender.addItem("Female");
		cbGender.addItem("All");
		
		cbStage.addItem("Mature");
		cbStage.addItem("Immature");
		cbStage.addItem("Undetermined");
		cbStage.addItem("All");
		
		cbTag.addItem("From API");
		
		jpTrackerRange.add(cbRange);
		jpTrackerGender.add(cbGender);
		jpTrackerStage.add(cbStage);
		jpTrackerTag.add(cbTag);
		
		JButton jbSearch = new JButton("Search");
		jpTrackerSearch.add(jbSearch, BorderLayout.NORTH);
		jpTrackerSearch.add(new JLabel(new ImageIcon(this.getClass().getResource("resources/sharkPic.jpg"))), BorderLayout.CENTER);
		
		JPanel jpAllDetails = new JPanel(new GridLayout(3, 1));
		jpAllDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		createDescriptions(3, jpAllDetails);
		overallFrame.add(jpAllDetails, BorderLayout.CENTER);
		
		add(overallFrame, BorderLayout.CENTER);
		add(new JLabel(shark.getAcknowledgement()), BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void createDescriptions(int n, JPanel panel){
		for(int i = 1; i <= n; i++){
			JPanel jpDetails1 = new JPanel(new GridLayout(2, 1));
			JPanel jpDetails2 = new JPanel(new GridLayout(6, 2));
			JPanel jpDetails3 = new JPanel(new BorderLayout());
			jpDetails1.add(jpDetails2);
			jpDetails1.add(jpDetails3);
			jpDetails2.add(new JLabel("Name: "));
			//might just make labels and update from view or whatnot
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			jpDetails2.add(new JLabel("Gender: "));
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			jpDetails2.add(new JLabel("Stage of Life: "));
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			jpDetails2.add(new JLabel("Species: "));
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			jpDetails2.add(new JLabel("Length: "));
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			jpDetails2.add(new JLabel("Weight: "));
			jpDetails2.add(new JTextArea(/*add in things needed*/));
			
			jpDetails3.add(new JLabel("Description"), BorderLayout.NORTH);
			jpDetails3.add(new JLabel("Enter a massive decription for whatever here but get it to update automatically"), BorderLayout.CENTER);
			jpDetails3.add(new JLabel("Last Ping: 34u823742873"), BorderLayout.SOUTH);
			
			jpDetails1.setBorder(BorderFactory.createEtchedBorder());
			
			panel.add(jpDetails1);
		}
		
	}
}
