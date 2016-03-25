package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import Jaws.View.ResultsPanel;
import Jaws.View.SearchFrame;

import javax.swing.JComboBox;


public class SearchListener implements ActionListener
{
	private Map<Shark, Ping> sharksPings;
	private List<Ping> pings;
	private JComboBox range, gender, stage, location;
	private Jaws jaws;
	private SearchFrame search;
	private ArrayList<Ping> nonDuplicates;
	private List<Ping> filteredSharks;
	private ArrayList<Ping> past24Hours;
	private ArrayList<Ping> pastWeek;
	private ArrayList<Ping> pastMonth;
	

	public SearchListener(SearchFrame search, Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location, ArrayList<Ping> past24Hours, ArrayList<Ping> pastWeek, ArrayList<Ping> pastMonth, ArrayList<Ping> nonDuplicates)
	{
		this.search = search;
		pings = new ArrayList<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;
		this.nonDuplicates = nonDuplicates;
		//search.getFaves().updateSharks(nonDuplicates);
		filteredSharks = new ArrayList<Ping>();
		this.past24Hours = past24Hours;
		this.pastWeek = pastWeek;
		this.pastMonth = pastMonth;
	}

	
	public void filterByRange(String range){
		switch(range){
		case "Last 24 Hours":
			for(Ping p: nonDuplicates){
					for(Ping p24: past24Hours){
						if(p.getTime().equals(p24.getTime())){
							filteredSharks.add(p);
							continue;
						}
					}
				}
			break;
		case "Last Week":
				for(Ping p: nonDuplicates){
					for(Ping pw: pastWeek){
						if(p.getTime().equals(pw.getTime())){
							filteredSharks.add(p);
							continue;
						}
					}
				}
			break;
		case "Last Month":
				for(Ping p: nonDuplicates){
					for(Ping pm: pastMonth){
						if(p.getTime().equals(pm.getTime())){
							filteredSharks.add(p);
							continue;
						}
					}
				}
			break;
		}
	}
	
	private void filterByGender(String gender){
		Iterator<Ping> it = filteredSharks.iterator();
		if(gender.equals("Male")){
			while(it.hasNext()){
				String tempShark = it.next().getName();
				if(!jaws.getShark(tempShark).getGender().equals("Male")){
					it.remove();
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
	
	private void filterByStage(String stage){
		Iterator<Ping> it = filteredSharks.iterator();
		switch(stage){
			case "Mature":
				while(it.hasNext()){
					String tempShark = it.next().getName();
					if(!jaws.getShark(tempShark).getStageOfLife().equals("Mature")){
						it.remove();
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
	
	public void filterByTagLoc(String location){
		if(!location.equals("All")){
			Iterator<Ping> it = filteredSharks.iterator();
			while(it.hasNext()){
				String tempShark = it.next().getName();
				if(!jaws.getShark(tempShark).getTagLocation().equals(location)){
					it.remove();
				}
			}
		}
	}
	
	//could be useful
	private ArrayList<String> pingToNames(ArrayList<Ping> pings){
		ArrayList<String> tempStrings = new ArrayList<String>();
		for(Ping p: pings){
			tempStrings.add(p.getName());
		}
		return tempStrings;
	}
	
	//need to order by first found pings
	private void createPanels(){
		search.getJpAllDetails().removeAll();
		Iterator<Ping> it = filteredSharks.iterator();
		while(it.hasNext()){
			Ping nextShark = it.next();
			search.putDescription(nextShark.getName());
			search.revalidate();
			//search.pack();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		filteredSharks.clear();
		String strRange = range.getSelectedItem().toString();
		String strGender = gender.getSelectedItem().toString();
		String strStage = stage.getSelectedItem().toString();
		String strLocation = location.getSelectedItem().toString();

		List<Shark> correctOrder = new ArrayList<Shark>();
		filterByRange(strRange); // filter the results by range
		filterByGender(strGender); // filter the results by gender
		filterByStage(strStage); // filter the results by stage of life
		filterByTagLoc(strLocation); // filter the results by tag location
		createPanels();
		
	}
}
