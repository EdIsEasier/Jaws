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

import javax.swing.JComboBox;


public class SearchListener implements ActionListener
{
	private List<Shark> allSharks;
	private List<Shark> foundSharks;
	private List<Ping> pings;
	private Map<Shark, Ping> sharksPings;
	private JComboBox range, gender, stage, location;
	private Jaws jaws;

	public SearchListener(Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location)
	{
		allSharks = new ArrayList<>();
		foundSharks = new ArrayList<>();
		pings = new ArrayList<>();
		sharksPings = new HashMap<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;

		//ArrayList<Ping> ping = jaws.past24Hours();
		/*
		for(int i = 0; i <= 1; i++){
			System.out.println(ping.get(i).getTime());
			System.out.println(ping.get(i).getName());
		}*/

		updateAllSharks();

		System.out.println("Constructor:");
		System.out.println(allSharks);
		System.out.println(sharksPings);
	}

	private void updateAllSharks()
	{
		for (String s : jaws.getSharkNames()) // Get all the shark names
			allSharks.add(jaws.getShark(s)); // Retrieve each of the sharks by their name
	}

	private void filterByRange(List<Shark> sharks, String range)
	{
		//Iterator<Shark> sharkIter = sharks.iterator();
		switch(range)
		{
			case "Last 24 Hours":
				for(Ping p : jaws.past24Hours()){
					Iterator<Shark> sharkIter = sharks.iterator();
					while (sharkIter.hasNext()) {
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							sharksPings.put(tempShark, p);
							//pings.add(p);
						}
					}
				}
				break;
			case "Last Week":
				for(Ping p : jaws.pastWeek()){
					Iterator<Shark> sharkIter = sharks.iterator();
					while (sharkIter.hasNext()) {
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							sharksPings.put(tempShark, p);
							//pings.add(p);
						}
					}
				}
				break;
			case "Last Month":
				for(Ping p : jaws.pastMonth()){
					Iterator<Shark> sharkIter = sharks.iterator();
					while (sharkIter.hasNext()) {
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							sharksPings.put(tempShark, p);
							//pings.add(p);
						}
					}
				}
				break;
		}
	}

	private void filterByGender(List<Shark> sharks, String gender)
	{
		Iterator<Shark> sharkIter = sharks.iterator();
		switch(gender)
		{
			case "Male":
				while(sharkIter.hasNext())
				{
					Shark tempShark = sharkIter.next();
					if (tempShark.getGender().equals("Female"))
					{
						sharkIter.remove();
						sharksPings.remove(tempShark);
					}
				}
				break;
			case "Female":
				while(sharkIter.hasNext())
				{
					Shark tempShark = sharkIter.next();
					if (tempShark.getGender().equals("Male"))
					{
						sharkIter.remove();
						sharksPings.remove(tempShark);
					}
				}
				break;
		}
	}

	private void filterByStage(List<Shark> sharks, String stage)
	{
		Iterator<Shark> sharkIter = sharks.iterator();
		switch(stage)
		{
			case "Mature":
				while(sharkIter.hasNext())
				{
					Shark tempShark = sharkIter.next();
					if (!tempShark.getStageOfLife().equals("Mature")){
						sharkIter.remove();
						sharksPings.remove(tempShark);
						//pings.remove(sharks.indexOf(s));
					}
				}
				break;
			case "Immature":
				while(sharkIter.hasNext())
				{
					Shark tempShark = sharkIter.next();
					if (!tempShark.getStageOfLife().equals("Immature")){
						sharkIter.remove();
						sharksPings.remove(tempShark);
						//pings.remove(sharks.indexOf(s));
					}
				}
				break;
			case "Undetermined":
				while(sharkIter.hasNext())
				{
					Shark tempShark = sharkIter.next();
					if (!tempShark.getStageOfLife().equals("Undetermined")){
						sharkIter.remove();
						sharksPings.remove(tempShark);
						//pings.remove(sharks.indexOf(tempShark));
					}
				}
				break;
		}
	}
	
	private void filterByTagLoc(List<Shark> sharks, String location)
	{
		if(!location.equals("All")){
			Iterator<Shark> sharkIter = sharks.iterator();
			int p = 0;
			while (sharkIter.hasNext()) {
				Shark tempShark = sharkIter.next();
				p++;
				if (!tempShark.getTagLocation().equals(location)){
					sharkIter.remove();
					sharksPings.remove(tempShark);
					//pings.remove(sharks.indexOf(tempShark));
				}
			}
		}
	}
	
	/*private void swapSharks(List<Shark> sList, Shark shark){
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
			Calendar nextDate = changeToDate(sharksPings, );
			if(thisDate.after(nextDate)){
				swapSharks(foundSharks, s);
				swapPings(pings, pings.get(foundSharks.indexOf(s)));
			}
		}
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
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String strRange = range.getSelectedItem().toString();
		String strGender = gender.getSelectedItem().toString();
		String strStage = stage.getSelectedItem().toString();
		String strLocation = location.getSelectedItem().toString();

		List<Shark> correctOrder = new ArrayList<Shark>();
		filterByRange(allSharks, strRange); // filter the results by range
		filterByGender(foundSharks, strGender); // filter the results by gender
		filterByStage(foundSharks, strStage); // filter the results by stage of life
		filterByTagLoc(foundSharks, strLocation); // filter the results by tag location
		//orderByTime();
		//createPanels(sharksPings);
		System.out.println("actionPerformed:");
		System.out.println(foundSharks);
		System.out.println(sharksPings);
	}
}
