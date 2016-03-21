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
	private ArrayList<String> nonDuplicates;

	public SearchListener(SearchFrame search, Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location)
	{
		this.search = search;
		pings = new ArrayList<>();
		sharksPings = new HashMap<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;
		nonDuplicates = deleteDuplicates();

		//ArrayList<Ping> ping = jaws.past24Hours();
		/*
		for(int i = 0; i <= 1; i++){
			System.out.println(ping.get(i).getTime());
			System.out.println(ping.get(i).getName());
		}*/

		System.out.println("Constructor:");
		System.out.println(nonDuplicates);
	}

	private void filterByRange(String range)
	{
		switch(range)
		{
			case "Last 24 Hours":
				for(Ping p : jaws.past24Hours()){
					for (String s : nonDuplicates)
					{
						if (s.equals(p.getName()))
						{
							sharksPings.put(jaws.getShark(s), p);
						}
					}
				}
				break;
			case "Last Week":
				for(Ping p : jaws.pastWeek()){
					for (String s : nonDuplicates)
					{
						if (s.equals(p.getName()))
						{
							sharksPings.put(jaws.getShark(s), p);
						}
					}
				}
				break;
			case "Last Month":
				for(Ping p : jaws.pastMonth()){
					for (String s : nonDuplicates)
					{
						if (s.equals(p.getName()))
						{
							sharksPings.put(jaws.getShark(s), p);
						}
					}
				}
				break;
		}
	}

	private void filterByGender(String gender)
	{
		Iterator sharksPingsIter = sharksPings.entrySet().iterator();
		switch(gender)
		{
			case "Male":
				while (sharksPingsIter.hasNext()) {
					Map.Entry<Shark, Ping> pair = (Map.Entry)sharksPingsIter.next();
					if (pair.getKey().getGender().equals("Female"))
					{
						sharksPingsIter.remove();
					}
				}
				break;
			case "Female":
				while (sharksPingsIter.hasNext()) {
					Map.Entry<Shark, Ping> pair = (Map.Entry)sharksPingsIter.next();
					if (pair.getKey().getGender().equals("Male"))
					{
						sharksPingsIter.remove();
					}
				}
				break;
		}
	}

	private void filterByStage(String stage)
	{
		Iterator it = sharksPings.entrySet().iterator();
		switch(stage)
		{
			case "Mature":
				while (it.hasNext()){
					Map.Entry<Shark, Ping> nextPair = (Map.Entry)it.next();
					if (!nextPair.getKey().getStageOfLife().equals("Mature")){
						it.remove();
					}
				}
				break;
			case "Immature":
				while (it.hasNext()){
					Map.Entry<Shark, Ping> nextPair = (Map.Entry)it.next();
					if (!nextPair.getKey().getStageOfLife().equals("Immature")){
						it.remove();
					}
				}
				break;
			case "Undetermined":
				while (it.hasNext()){
					Map.Entry<Shark, Ping> nextPair = (Map.Entry)it.next();
					if (!nextPair.getKey().getStageOfLife().equals("Mature")){
						it.remove();
					}
				}
				break;
		}
	}
	
	private void filterByTagLoc(String location)
	{
		if(!location.equals("All")){
			Iterator it = sharksPings.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry<Shark, Ping> nextPair = (Map.Entry)it.next();
				if (!nextPair.getKey().getTagLocation().equals(location)){
					it.remove();
				}
			}
		}
	}
	
	private ArrayList<String> deleteDuplicates(){
		ArrayList<Ping> tempPings = new ArrayList<Ping>();
		ArrayList<Ping> pingToRemove = new ArrayList<Ping>();
		tempPings.addAll(jaws.pastMonth());
		for(int i = 0; i < tempPings.size(); i++){
			Ping tempPing = tempPings.get(i);
			for(int j = i + 1; j < tempPings.size(); j++){
				Ping tempPing2 = tempPings.get(j);
				if(tempPing.getName().equals(tempPing2.getName())){
					if(changePingToDate(tempPing2).before(changePingToDate(tempPing))){
						pingToRemove.add(tempPing2);
					}
				}
			}
		}
		tempPings.removeAll(pingToRemove);
		return pingToNames(tempPings);
	}
	
	private ArrayList<String> pingToNames(ArrayList<Ping> pings){
		ArrayList<String> tempStrings = new ArrayList<String>();
		for(Ping p: pings){
			tempStrings.add(p.getName());
		}
		return tempStrings;
	}
	
	private void swapSharks(List<Shark> sList, Shark shark){
		sList.set(sList.indexOf(shark), sList.get(sList.indexOf(shark) + 1));
		sList.set(sList.indexOf(shark ) + 1, shark);
	}
	
	private void swapPings(List<Ping> pList, Ping pings){
		pList.set(pList.indexOf(pings), pList.get(pList.indexOf(pings) + 1));
		pList.set(pList.indexOf(pings ) + 1, pings);
	}
	
	private void orderByTime(){
		Iterator it = sharksPings.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry nextPair = (Map.Entry)it.next();
			Calendar thisDate = changeToDate(sharksPings, nextPair.getKey());
			//Calendar nextDate = changeToDate(sharksPings, );
			//if(thisDate.after(nextDate)){
				//swapSharks(foundSharks, s);
				//swapPings(pings, pings.get(foundSharks.indexOf(s)));
			}
		}
	//}
	
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
	
	private Calendar changeToDate(Map<Shark, Ping> sPings, Object shark){
		Calendar calendar = new GregorianCalendar();
		Ping p = sPings.get(shark);
		String[] dates = p.getTime().split(" ");
		String[] sDate = dates[0].split("-");
		String[] sTime = dates[1].split(":");
		int[] date = new int[3];
		int[] time = new int[3];
		for(int i = 0; i < 3; i++){
			date[i] = Integer.parseInt(sDate[i]);
			time[i] = Integer.parseInt(sTime[i]);
		}
		calendar.set(date[0], date[1], date[2], time[0], time[1], time[2]);
		return calendar;
	}
	
	
	private void createPanels(Map<Shark, Ping> sharkPing){
		Iterator it = sharkPing.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry nextPair = (Map.Entry)it.next();
			ResultsPanel rPanel = new ResultsPanel((Shark)nextPair.getKey(), (Ping)nextPair.getValue());
		}
	}
	
	private void createPanels(){
		Iterator it = sharksPings.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Shark, Ping> nextPair = (Map.Entry)it.next();
			search.createDescriptions(nextPair.getKey(), nextPair.getValue());
			//search.repaint();
			search.revalidate();
			search.pack();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String strRange = range.getSelectedItem().toString();
		String strGender = gender.getSelectedItem().toString();
		String strStage = stage.getSelectedItem().toString();
		String strLocation = location.getSelectedItem().toString();

		List<Shark> correctOrder = new ArrayList<Shark>();
		filterByRange(strRange); // filter the results by range
		System.out.println("actionPerformed (after range filter):");
		System.out.println(sharksPings);
		filterByGender(strGender); // filter the results by gender
		filterByStage(strStage); // filter the results by stage of life
		filterByTagLoc(strLocation); // filter the results by tag location
		//orderByTime();
		//createPanels(sharksPings);
		System.out.println("actionPerformed (after all filters):");
		System.out.println(sharksPings);
		createPanels();
		
	}
}
