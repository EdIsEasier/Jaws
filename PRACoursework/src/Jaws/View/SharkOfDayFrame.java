package Jaws.View;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SharkOfDayFrame extends JFrame{
	private JLabel name;
	private JLabel video;
	
	public SharkOfDayFrame(String name, String video) {
		super("Shark Of The Day!");
		this.name = new JLabel(name);
		this.video = new JLabel(video);
		setLayout(new GridLayout(2, 1));
		add(this.name);
		add(this.video);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
