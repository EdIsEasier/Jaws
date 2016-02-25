package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
		add(jpTracker, BorderLayout.WEST);
		jpTracker.add(jpTrackerRange);
		jpTracker.add(jpTrackerRange);
		jpTracker.add(jpTrackerStage);
		jpTracker.add(jpTrackerTag);
		jpTracker.add(jpTrackerSearch);
		
		jpTrackerRange.add(new JLabel("Tracking Range"));
		jpTrackerGender.add(new JLabel("Gender"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
