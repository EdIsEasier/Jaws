package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// SUPER EXPERIMENTAL AND UNFINISHED

public class SearchListener implements ActionListener
{
	private List<Shark> allSharks;
	private List<Shark> foundSharks;
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
		List<Shark> tempSharks = new ArrayList<Shark>();
		Iterator<Shark> sharkIter = sharks.iterator();
		switch(range)
		{
			case "Last 24 Hours":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.past24Hours()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							tempSharks.add(tempShark);
						}
					}
				}
				break;
			case "Last Week":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.pastWeek()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							tempSharks.add(tempShark);
						}
					}
				}
				break;
			case "Last Month":
				while (sharkIter.hasNext()) {
					for(Ping p : jaws.pastMonth()){
						Shark tempShark = sharkIter.next();
						if(tempShark.getName().equals(p.getName())){
							tempSharks.add(tempShark);
						}
					}
				}
				break;
		}
		addSharks(tempSharks);
	}

	private void filterByGender(List<Shark> sharks, String gender)
	{
		switch(gender)
		{
			case "Male":
				for (Shark s : sharks)
				{
					if (s.getGender().equals("Female"))
						sharks.remove(s);
				}
				break;
			case "Female":
				for (Shark s : sharks)
				{
					if (s.getGender().equals("Male"))
						sharks.remove(s);
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
					if (!s.getStageOfLife().equals("Mature"))
						sharks.remove(s);
				}
				break;
			case "Immature":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Immature"))
						sharks.remove(s);
				}
				break;
			case "Undetermined":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Undetermined"))
						sharks.remove(s);
				}
				break;
		}
	}
	
	private void filterByTagLoc(List<Shark> sharks, String location)
	{
		Iterator<Shark> sharkIter = sharks.iterator();
		while (sharkIter.hasNext()) {
			if (!sharkIter.next().getTagLocation().equals(location))
				sharkIter.remove();
		}
	}
	
	private void addSharks(List<Shark> sharkList){
		for(Shark sh: sharkList){
			foundSharks.add(sh);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		filterByRange(foundSharks, range); // filter the results by range
		filterByGender(foundSharks, gender); // filter the results by gender
		filterByStage(foundSharks, stage); // filter the results by stage of life
		filterByTagLoc(foundSharks, location); // filter the results by tag location
		System.out.println(foundSharks);
	}
}
