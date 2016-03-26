package Jaws.View;

import java.awt.GridLayout;

import javax.swing.JFrame;

import api.jaws.Shark;

/**
 * Frame to hold the sharks details you are comparing
 * 
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class CompareView extends JFrame{
	private SearchFrame search;
	
	/**
	 * Constructor the set the layout of the frame, initialise the fields and add a window listener
	 * 
	 * @param search the SearchFrame
	 */
	public CompareView(SearchFrame search) {
		super("Comparison");
		setLayout(new GridLayout(1, 2));
		this.search = search;
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {		//on the close event of this frame
                search.setCompare(null);		//set the CompareView in search to null
                dispose();
            }
        });
		
		pack();
	}
	
	/**
	 * adds a panel with all of the shark details you want to compare to the frame
	 * 
	 * @param shark the shark you want to compare
	 * @see ComparisonPanel
	 */
	public void addPanels(Shark shark){
		ComparisonPanel compare = new ComparisonPanel(shark);	//create a panel with the shark details
		add(compare);		//add it to the frame
		this.revalidate();	//refresh the frame
		pack();
	}

}
