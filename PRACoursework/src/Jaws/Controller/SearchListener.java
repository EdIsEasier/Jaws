package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


// SUPER EXPERIMENTAL AND UNFINISHED

public class SearchListener implements ActionListener
{
	private List<Shark> allSharks;
	private List<Shark> foundSharks;
	private String range, gender, stage, location;
	private Jaws jaws;

	public SearchListener(String range, String gender, String stage, String location)
	{
		allSharks = new ArrayList<>();
		foundSharks = new ArrayList<>();
		jaws = new Jaws("jphHPbni3MIBmMKu","jbB8OPuNG5Sxw11c");
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;

		updateAllSharks();
	}

	private void updateAllSharks()
	{
		for (String s : jaws.getSharkNames()) // Get all the shark names
		{
			allSharks.add(jaws.getShark(s)); // Retrieve each of the sharks by their name
		}
	}

	private void filterByRange(List<Shark> sharks, String range)
	{
		switch(range)
		{
			case "Last 24 hours":
				for (Ping p : jaws.past24Hours()) // Get all the sharks that have been detected in the last 24 hrs
					sharks.add(jaws.getShark(p.getName())); // Add each of those sharks to our passed in result list
				break;
			case "Last Week":
				for (Ping p : jaws.pastWeek())
					sharks.add(jaws.getShark(p.getName()));
				break;
			case "Last Month":
				for (Ping p : jaws.pastMonth())
					sharks.add(jaws.getShark(p.getName()));
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
					{
						sharks.remove(s);
					}
				}
				break;
			case "Immature":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Immature"))
					{
						sharks.remove(s);
					}
				}
				break;
			case "Undetermined":
				for (Shark s : sharks)
				{
					if (!s.getStageOfLife().equals("Undetermined"))
					{
						sharks.remove(s);
					}
				}
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		foundSharks.addAll(allSharks); // add all the sharks to the results
		filterByRange(foundSharks, range); // filter the results by range
		filterByGender(foundSharks, gender); // filter the results by gender
		filterByStage(foundSharks, stage); // filter the results by stage of life
	}
}
