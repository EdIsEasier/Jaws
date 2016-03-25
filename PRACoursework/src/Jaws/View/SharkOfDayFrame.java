package Jaws.View;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Frame for showing the shark of the day
 * 
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class SharkOfDayFrame extends JFrame{
	private JLabel name;	//name of the shark
	private JLabel video;	//the video
	
	/**
	 * constructor to set up the components of the frame and set the values of them
	 * 
	 * @param name	the name of the shark
	 * @param video	the video url
	 */
	public SharkOfDayFrame(String name, String video) {
		super("Shark Of The Day!");
		this.name = new JLabel(name);		//the name of the shark passed in
		this.video = new JLabel(video);		//the video url passed in
		setLayout(new GridLayout(2, 1));
		add(this.name);		//add the labels to the frame
		add(this.video);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
