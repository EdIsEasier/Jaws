package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Jaws.View.ResultsPanel;


public class SearchListener implements ActionListener
{
	private List<Shark> allSharks;
	private List<Shark> foundSharks;
	private List<Ping> pings;
	private String range, gender, stage, location;
	private Jaws jaws;

	public SearchListener(Jaws jaws, String range, String gender, String stage, String location)
	{
		allSharks = new ArrayList<>();
		foundSharks = new ArrayList<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;

		updateAllSharks();
	}

	private void updateAllSharks()
	{
		for (String s : jaws.getSharkNames()) // Get all the shark names
			allSharks.add(jaws.getShark(s)); // Retrieve each of the sharks by their name
	}

	private void filterByRange(List<Shark> sharks, String range)
	{
		Iterator<Shark> sharkIter = sharks.iterator();
		switch(range)
		{
			case "Last 24 Hours":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.past24Hours()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							pings.add(p);
						}
					}
				}
				break;
			case "Last Week":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.pastWeek()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							pings.add(p);
							
						}
					}
				}
				break;
			case "Last Month":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.pastMonth()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							pings.add(p);
						}
					}
				}
				break;
		}
	}

	private void filterByGender(List<Shark> sharks, String gender)
	{
		switch(gender)
		{
			case "Male":
				for (Shark s : sharks)
				{
					if (s.getGender().equals("Female")){
						sharks.remove(s);
						pings.remove(sharks.indexOf(s));
					}
				}
				break;
			case "Female":
				for (Shark s : sharks)
				{
					if (s.getGender().equals("Male")){
						sharks.remove(s);
						pings.remove(sharks.indexOf(s));
					}
				}
				break;
		}
	}

	private void filterByStage(List<Shark> sharks, String stage)
	{
		switch(stage)
		{
			case "Mature":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Mature")){
						sharks.remove(s);
						pings.remove(sharks.indexOf(s));
					}
				}
				break;
			case "Immature":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Immature")){
						sharks.remove(s);
						pings.remove(sharks.indexOf(s));
					}
				}
				break;
			case "Undetermined":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Undetermined")){
						sharks.remove(s);
						pings.remove(sharks.indexOf(s));
					}
				}
				break;
		}
	}
	
	private void filterByTagLoc(List<Shark> sharks, String location)
	{
		Iterator<Shark> sharkIter = sharks.iterator();
		while (sharkIter.hasNext()) {
			Shark tempShark = sharkIter.next();
			if (!tempShark.getTagLocation().equals(location)){
				sharkIter.remove();
				pings.remove(sharks.indexOf(tempShark));
			}
		}
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
		for(Shark s: foundSharks){
			Date thisDate = changeToDate(getPing(s));
			Date nextDate = changeToDate(pings.get(foundSharks.indexOf(s) + 1));
			if(thisDate.after(nextDate)){
				swapSharks(foundSharks, s);
				swapPings(pings, pings.get(foundSharks.indexOf(s)));
			}
		}
	}
	
	private void createPanels(List<Shark> sList){
		for(Shark s: foundSharks){
			ResultsPanel rPanel = new ResultsPanel(s, changeToDate(getPing(s)));
		}
	}
	
	private Ping getPing(Shark shark){
		return pings.get(foundSharks.indexOf(shark));
	}
	
	private Date changeToDate(Ping p){
		Date date = new Date(p.getTime());
		return date;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		List<Shark> correctOrder = new ArrayList<Shark>();
		filterByRange(allSharks, range); // filter the results by range
		filterByGender(foundSharks, gender); // filter the results by gender
		filterByStage(foundSharks, stage); // filter the results by stage of life
		filterByTagLoc(foundSharks, location); // filter the results by tag location
		orderByTime();
	}
}
