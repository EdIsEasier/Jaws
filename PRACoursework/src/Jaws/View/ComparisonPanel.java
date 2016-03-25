package Jaws.View;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import api.jaws.Shark;

/**
 * JPanel to hold all of the details of the sharks to compare
 * 
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class ComparisonPanel extends JPanel{
	private Shark shark; //the shark that you want to compare
	
	/**
	 * Constructo to set the layout and initialise the fields
	 * 
	 * @param shark the shark you want to compare
	 */
	public ComparisonPanel(Shark shark) {
		super(new GridLayout(6, 2));
		this.shark = shark;
		createDetails();
	}
	
	/**
	 * add all of the details of the sharks to the panel
	 */
	public void createDetails(){
		add(new JLabel("Name: "));
		add(new JTextArea(shark.getName()){{setEditable(false);}});
		add(new JLabel("Gender: "));
		add(new JTextArea(shark.getGender()){{setEditable(false);}});
		add(new JLabel("Stage of Life: "));
		add(new JTextArea(shark.getStageOfLife()){{setEditable(false);}});
		add(new JLabel("Species: "));
		add(new JTextArea(shark.getSpecies()){{setEditable(false);}});
		add(new JLabel("Length: "));
		add(new JTextArea(shark.getLength()){{setEditable(false);}});
		add(new JLabel("Weight: "));
		add(new JTextArea(shark.getWeight()){{setEditable(false);}});
	}

}
