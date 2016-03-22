package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import api.jaws.Ping;
import api.jaws.Shark;

public class ResultsPanel extends JPanel{
	private Shark shark;
	private Ping date;
	
	public ResultsPanel(Shark shark, Ping ping) {
		super(new GridLayout(2, 1));
		
		this.shark = shark;
		this.date = ping;
		
		this.setBorder(BorderFactory.createEtchedBorder());
		createWidgets();
	}
	
	public void createWidgets(){
		JPanel jpDetails2 = new JPanel(new GridLayout(6, 2));
		JPanel jpDetails3 = new JPanel(new BorderLayout());
		jpDetails2.add(new JLabel("Name: "));
		//might just make labels and update from view or whatnot
		jpDetails2.add(new JTextArea(shark.getName()));
		jpDetails2.add(new JLabel("Gender: "));
		jpDetails2.add(new JTextArea(shark.getGender()));
		jpDetails2.add(new JLabel("Stage of Life: "));
		jpDetails2.add(new JTextArea(shark.getStageOfLife()));
		jpDetails2.add(new JLabel("Species: "));
		jpDetails2.add(new JTextArea(shark.getSpecies()));
		jpDetails2.add(new JLabel("Length: "));
		jpDetails2.add(new JTextArea(shark.getLength()));
		jpDetails2.add(new JLabel("Weight: "));
		jpDetails2.add(new JTextArea(shark.getWeight()));
		
		jpDetails3.add(new JLabel("Description"), BorderLayout.NORTH);
		JTextArea desc = new JTextArea(shark.getDescription());
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		jpDetails3.add(desc, BorderLayout.CENTER);
		jpDetails3.add(new JLabel("Last Ping: " + date.getTime()), BorderLayout.SOUTH);
		
		add(jpDetails2);
		add(jpDetails3);
	}

}
