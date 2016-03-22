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

	public SearchListener(SearchFrame search, Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location)
	{
		this.search = search;
		pings = new ArrayList<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;
		nonDuplicates = new ArrayList<>();
		filteredSharks = new ArrayList<Ping>();
	}

	
	public void filterByRange(String range){
		switch(range){
		case "Last 24 Hours":
			for(Ping p: nonDuplicates){
					for(Ping p24: jaws.past24Hours()){
						if(p.getTime().equals(p24.getTime())){
							filteredSharks.add(p);
							continue;
						}
					}
				}
			break;
		case "Last Week":
				for(Ping p: nonDuplicates){
					for(Ping pw: jaws.pastWeek()){
						if(p.getTime().equals(pw.getTime())){
							filteredSharks.add(p);
							continue;
						}
					}
				}
			break;
		case "Last Month":
				for(Ping p: nonDuplicates){
					for(Ping pm: jaws.pastMonth()){
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
	
	private ArrayList<Ping> deleteDuplicates(){
		ArrayList<Ping> tempPings = new ArrayList<Ping>();
		ArrayList<Ping> tempPings2 = new ArrayList<Ping>();
		tempPings.addAll(jaws.pastMonth());
		tempPings2.addAll(jaws.pastMonth());
		ListIterator<Ping> it = tempPings.listIterator(0);
		while(it.hasNext()){
			Ping tempPing = it.next();
			ListIterator<Ping> it2 = tempPings2.listIterator(0);
			while(it2.hasNext()){
				Ping tempPing2 = it2.next();
				if(tempPing.getName().equals(tempPing2.getName())){
					if(changePingToDate(tempPing2).before(changePingToDate(tempPing))){
						it2.remove();
					}
				}
			}
		}
		return tempPings2;
	}
	
	//could be useful
	private ArrayList<String> pingToNames(ArrayList<Ping> pings){
		ArrayList<String> tempStrings = new ArrayList<String>();
		for(Ping p: pings){
			tempStrings.add(p.getName());
		}
		return tempStrings;
	}
	
	private Calendar changePingToDate(Ping ping){
		Calendar calendar = new GregorianCalendar();
		String time = ping.getTime();
		String[] dates = time.split(" ");
		String[] sDate = dates[0].split("-");
		String[] sTime = dates[1].split(":");
		int[] date = new int[3];
		int[] iTime = new int[3];
		for(int i = 0; i < 3; i++){
			date[i] = Integer.parseInt(sDate[i]);
			iTime[i] = Integer.parseInt(sTime[i]);
		}
		calendar.set(date[0], date[1], date[2], iTime[0], iTime[1], iTime[2]);
		return calendar;
	}
	
	//need to order by first found pings
	private void createPanels(){
		search.getJpAllDetails().removeAll();
		Iterator<Ping> it = filteredSharks.iterator();
		while(it.hasNext()){
			Ping nextShark = it.next();
			search.createDescriptions(jaws.getShark(nextShark.getName()), nextShark);
			search.revalidate();
			//search.pack();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		filteredSharks.clear();
		nonDuplicates = deleteDuplicates();
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
		System.out.println("actionPerformed (after range filter):");
		System.out.println(jaws.past24Hours());
		System.out.println(nonDuplicates);
		System.out.println(filteredSharks);
		
	}
}
