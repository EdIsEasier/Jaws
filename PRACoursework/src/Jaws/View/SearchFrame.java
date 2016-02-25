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

public class SearchFrame extends JFrame{
	
	public SearchFrame(){
		super("Search");
		
		createWidgets();
	}

	private void createWidgets() {
		setLayout(new BorderLayout());
		JPanel jpTracker = new JPanel(new GridLayout(5, 1));
		JPanel jpTrackerRange = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerGender = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerStage = new JPanel(new GridLayout (2, 1));
		JPanel jpTrackerTag = new JPanel(new GridLayout(2, 1));
		JPanel jpTrackerSearch = new JPanel(new BorderLayout());
		
		jpTrackerRange.setBorder(BorderFactory.createLineBorder(Color.black));
		jpTrackerGender.setBorder(BorderFactory.createLineBorder(Color.black));
		jpTrackerStage.setBorder(BorderFactory.createLineBorder(Color.black));
		jpTrackerTag.setBorder(BorderFactory.createLineBorder(Color.black));
		jpTrackerSearch.setBorder(BorderFactory.createLineBorder(Color.black));
		
		add(jpTracker, BorderLayout.WEST);
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
		
		jpTrackerRange.add(cbRange);
		jpTrackerGender.add(cbGender);
		jpTrackerStage.add(cbStage);
		jpTrackerTag.add(cbTag);
		
		JButton jbSearch = new JButton();
		jpTrackerSearch.add(jbSearch, BorderLayout.NORTH);
		jpTrackerSearch.add(new JLabel(new ImageIcon(this.getClass().getResource("resources/randypic.jpg"))), BorderLayout.CENTER);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
