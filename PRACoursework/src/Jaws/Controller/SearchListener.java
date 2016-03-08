package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import Jaws.View.ResultsPanel;

import javax.swing.JComboBox;


public class SearchListener implements ActionListener
{
	private List<Shark> allSharks;
	private List<Shark> foundSharks;
	private List<Ping> pings;
	private JComboBox range, gender, stage, location;
	private Jaws jaws;

	public SearchListener(Jaws jaws, JComboBox range, JComboBox gender, JComboBox stage, JComboBox location)
	{
		allSharks = new ArrayList<>();
		foundSharks = new ArrayList<>();
		pings = new ArrayList<>();
		this.jaws = jaws;
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;
		ArrayList<Ping> ping = jaws.past24Hours();

		for(int i = 0; i <= 1; i++){
			System.out.println(ping.get(i).getTime());
			System.out.println(ping.get(i).getName());
		}

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
				for(Ping p : jaws.past24Hours()){
					while (sharkIter.hasNext()) {
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							pings.add(p);
						}
					}
				}
				break;
			case "Last Week":
				for(Ping p : jaws.pastWeek()){
					while (sharkIter.hasNext()) {
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							foundSharks.add(tempShark);
							pings.add(p);
						}
					}
				}
				break;
			case "Last Month":
				for(Ping p : jaws.pastMonth()){
					while (sharkIter.hasNext()) {
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
		int p = 0;
		while (sharkIter.hasNext()) {
			Shark tempShark = sharkIter.next();
			p++;
			if (!tempShark.getTagLocation().equals(location)){
				sharkIter.remove();
				pings.remove(sharks.indexOf(p));
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
			Calendar thisDate = changeToDate(getPing(s));
			Calendar nextDate = changeToDate(pings.get(foundSharks.indexOf(s) + 1));
			if(thisDate.after(nextDate)){
				swapSharks(foundSharks, s);
				swapPings(pings, pings.get(foundSharks.indexOf(s)));
			}
		}
	}
	
	private void createPanels(List<Shark> sList, List<Ping> orderedPings){
		for(Shark s: foundSharks){
			ResultsPanel rPanel = new ResultsPanel(s, orderedPings.get(foundSharks.indexOf(s)));
		}
	}
	
	private Ping getPing(Shark shark){
		return pings.get(foundSharks.indexOf(shark));
	}
	
	private Calendar changeToDate(Ping p){
		Calendar calendar = new GregorianCalendar();
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
		orderByTime();
		createPanels(foundSharks, pings);
	}
}
