package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Jaws.View.SearchFrame;

import javax.swing.JComboBox;

/**
 * SearchListener class is responsible for retrieving the right
 * sharks based on the provided criteria
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class SearchListener implements ActionListener
{
	private JComboBox range, gender, stage, location; // combo boxes with the user selected criteria
	private Jaws jaws; // reference to the Jaws API
	private SearchFrame search; // reference to the Search frame
	private ArrayList<Ping> nonDuplicates; // list of pings with no duplicates
	private List<Ping> filteredSharks; // list of filtered pings
	private ArrayList<Ping> past24Hours; // list of pings from the past 24 hours
	private ArrayList<Ping> pastWeek; // list of pings from the last week
	private ArrayList<Ping> pastMonth; // list of pings from the last month

	/**
	 * Constructor that initialises all the fields that are passed in
	 *
	 * @param search reference to the Search window
	 * @param jaws reference to the Jaws API
	 * @param range shark range
	 * @param gender shark gender
	 * @param stage shark life stage
	 * @param location shark tag location
	 * @param past24Hours pings from the last 24 hours
	 * @param pastWeek pings from the last week
	 * @param pastMonth pings from the last month
	 * @param nonDuplicates pings with no duplicates
	 */
	public SearchListener(SearchFrame search, Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location, ArrayList<Ping> past24Hours, ArrayList<Ping> pastWeek, ArrayList<Ping> pastMonth, ArrayList<Ping> nonDuplicates)
	{
		this.search = search;
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;
		this.nonDuplicates = nonDuplicates;
		filteredSharks = new ArrayList<Ping>();
		this.past24Hours = past24Hours;
		this.pastWeek = pastWeek;
		this.pastMonth = pastMonth;
	}

	/**
	 * Filters the pings by range
	 *
	 * @param range range
	 * @see Ping
	 */
	public void filterByRange(String range){
		switch(range){
		case "Last 24 Hours":
			for(Ping p: nonDuplicates){ // loop over all the non-duplicate sharks
					for(Ping p24 : past24Hours){ // loop over all the pings from the past 24 hours
						if(p.getTime().equals(p24.getTime())){ // if their time matches
							filteredSharks.add(p); // add that ping to all the filtered ones
							break;
						}
					}
				}
			break;
		case "Last Week":
				for(Ping p : nonDuplicates){
					for(Ping pw: pastWeek){
						if(p.getTime().equals(pw.getTime())){
							filteredSharks.add(p);
							break;
						}
					}
				}
			break;
		case "Last Month":
				for(Ping p : nonDuplicates){
					for(Ping pm: pastMonth){
						if(p.getTime().equals(pm.getTime())){
							filteredSharks.add(p);
							break;
						}
					}
				}
			break;
		}
	}

	/**
	 * Filter the pings by gender
	 *
	 * @param gender gender
	 * @see Jaws
	 */
	private void filterByGender(String gender){
		Iterator<Ping> it = filteredSharks.iterator(); // get an array iterator
		if(gender.equals("Male")){
			while(it.hasNext()){ // while there is a shark
				String tempShark = it.next().getName(); // get its name
				if(!jaws.getShark(tempShark).getGender().equals("Male")){ // if it's not male
					it.remove(); // remove it
				}
			}
		}
		if(gender.equals("Female")){
			while(it.hasNext()){
				String tempShark = it.next().getName();
				if(!jaws.getShark(tempShark).getGender().equals("Female")){
					it.remove();
				}
			}
		}
	}

	/**
	 * Filter the pings by life stage
	 *
	 * @param stage life stage
	 * @see Jaws
	 */
	private void filterByStage(String stage){
		Iterator<Ping> it = filteredSharks.iterator(); // get an array iterator
		switch(stage){
			case "Mature":
				while(it.hasNext()){ // while there is a shark
					String tempShark = it.next().getName(); // get its name
					if(!jaws.getShark(tempShark).getStageOfLife().equals("Mature")){ // if it's not a mature shark
						it.remove(); // remove it
					}
				}
				break;
			case "Immature":
				while(it.hasNext()){
					String tempShark = it.next().getName();
					if(!jaws.getShark(tempShark).getStageOfLife().equals("Immature")){
						it.remove();
					}
				}
				break;
			case "Undetermined":
				while(it.hasNext()){
					String tempShark = it.next().getName();
					if(!jaws.getShark(tempShark).getStageOfLife().equals("Undetermined")){
						it.remove();
					}
				}
				break;
		}
	}

	/**
	 * Filter the pings by tag location
	 *
	 * @param location tag location
	 * @see Jaws
	 */
	public void filterByTagLoc(String location){
		if(!location.equals("All")){ // if the user didn't select "All"
			Iterator<Ping> it = filteredSharks.iterator(); // get an array iterator
			while(it.hasNext()){ // while there is another shark
				String tempShark = it.next().getName(); // get its name
				if(!jaws.getShark(tempShark).getTagLocation().equals(location)){ // if the shark was tagged elsewhere
					it.remove(); // remove it
				}
			}
		}
	}

	/**
	 * Creates shark description panels for each of the filtered sharks
	 * 
	 * @see SearchFrame
	 */
	private void createPanels(){
		search.getJpAllDetails().removeAll(); // remove any existing shark descriptions
		Iterator<Ping> it = filteredSharks.iterator();
		while(it.hasNext()){
			Ping nextShark = it.next(); // get the next ping
			search.putDescription(nextShark.getName()); // add a description of that shark
			search.revalidate();
		}
	}
	
	/**
	 * filters out all of the sharks on the search button press and displays the sharks in the categories that you have chosen on the SearchFrame
	 * 
	 * @param ActionEvent the button press
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		filteredSharks.clear(); // clear all the filtered sharks so there are no duplicates
		// get all the criteria
		String strRange = range.getSelectedItem().toString();
		String strGender = gender.getSelectedItem().toString();
		String strStage = stage.getSelectedItem().toString();
		String strLocation = location.getSelectedItem().toString();

		filterByRange(strRange); // filter the results by range
		filterByGender(strGender); // filter the results by gender
		filterByStage(strStage); // filter the results by stage of life
		filterByTagLoc(strLocation); // filter the results by tag location
		createPanels(); // create panels for all the filtered sharks
	}
}
