package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ResultsPanel extends JPanel{

	public ResultsPanel() {
		super(new GridLayout(2, 1));
		
		this.setBorder(BorderFactory.createEtchedBorder());
		createWidgets();
	}
	
	public void createWidgets(){
		JPanel jpDetails2 = new JPanel(new GridLayout(6, 2));
		JPanel jpDetails3 = new JPanel(new BorderLayout());
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
		
		add(jpDetails2);
		add(jpDetails3);
	}

}
