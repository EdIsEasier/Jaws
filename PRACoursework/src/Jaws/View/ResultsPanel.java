package Jaws.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Jaws.Controller.FavouriteButtonListener;
import api.jaws.Ping;
import api.jaws.Shark;

/**
 * ResultsPanel class is a description panel used to show all the information
 * about each of the sharks
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class ResultsPanel extends JPanel{
	private Shark shark; // shark whose information will be shown
	private Ping date; // sharl's ping
	private Favourites faves; // reference to the Favourites window
	private JButton follow; // follow button
	private JButton compare; // compare button
	private SearchFrame search; // reference to the search frame

	/**
	 * Constructor that sets up the panel
	 *
	 * @param search reference to the search frame
	 * @param shark shark whose information will be shown
	 * @param ping shark's ping
	 * @param faves reference to the Favourites window
	 */
	public ResultsPanel(SearchFrame search,Shark shark, Ping ping, Favourites faves) {
		super(new GridLayout(2, 1));
		
		this.shark = shark;
		this.date = ping;
		this.faves = faves;
		this.search = search;
		setName(shark.getName());

		this.setBorder(BorderFactory.createEtchedBorder());
		createWidgets();
	}

	/**
	 * Initialises and creates all the components for the window
	 */
	private void createWidgets(){
		JPanel jpDetails2 = new JPanel(new GridLayout(6, 2));
		JPanel jpDetails3 = new JPanel(new BorderLayout());

		jpDetails2.add(new JLabel("Name: "));
		jpDetails2.add(new JTextArea(shark.getName()){{setEditable(false);}});
		jpDetails2.add(new JLabel("Gender: "));
		jpDetails2.add(new JTextArea(shark.getGender()){{setEditable(false);}});
		jpDetails2.add(new JLabel("Stage of Life: "));
		jpDetails2.add(new JTextArea(shark.getStageOfLife()){{setEditable(false);}});
		jpDetails2.add(new JLabel("Species: "));
		jpDetails2.add(new JTextArea(shark.getSpecies()){{setEditable(false);}});
		jpDetails2.add(new JLabel("Length: "));
		jpDetails2.add(new JTextArea(shark.getLength()){{setEditable(false);}});
		jpDetails2.add(new JLabel("Weight: "));
		jpDetails2.add(new JTextArea(shark.getWeight()){{setEditable(false);}});
		
		jpDetails3.add(new JLabel("Description"), BorderLayout.NORTH);
		JTextArea desc = new JTextArea(shark.getDescription());
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		jpDetails3.add(desc, BorderLayout.CENTER);
		JPanel jpBottomStrip = new JPanel(new BorderLayout());
		jpBottomStrip.add(new JLabel("Last Ping: " + date.getTime()), BorderLayout.WEST);
		
		follow = new JButton("Follow");
		follow.addActionListener(new FavouriteButtonListener(faves, shark));
		compare = new JButton("Compare");
		JPanel followCompare = new JPanel(new GridLayout(2, 1));
		followCompare.add(follow);
		followCompare.add(compare);

		compare.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(search.getCompare() == null){
					search.setCompare(new CompareView(search));
					search.getCompare().addPanels(shark);
				}
				else{
					search.getCompare().addPanels(shark);
				}
				search.getCompare().setVisible(true);
			}

		});

		jpBottomStrip.add(followCompare, BorderLayout.EAST);
		
		jpDetails3.add(jpBottomStrip, BorderLayout.SOUTH);
		
		add(jpDetails2);
		add(jpDetails3);
	}

	/**
	 * Gets the follow button
	 *
	 * @return JButton button
	 */
	public JButton getFollowed(){
		return follow;
	}
}
