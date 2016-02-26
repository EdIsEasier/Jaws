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
		jaws = new Jaws("jphHPbni3MIBmMKu","jbB8OPuNG5Sxw11c");
		this.range = range;
		this.gender = gender;
		this.stage = stage;
		this.location = location;

		updateAllSharks();
	}

	private void updateAllSharks()
	{
		// ArrayList<String> sharkNames = jaws.getSharkNames();
		for (String s : jaws.getSharkNames())
		{
			allSharks.add(jaws.getShark(s));
		}
	}

	private void filterByRange(List<Shark> sharks, String range)
	{
		ArrayList<Ping> neededSharks;
		switch(range)
		{
			case "All":
				sharks.addAll(allSharks);
				break;
			case "Last 24 hours":
				neededSharks = jaws.past24Hours();
				for (Ping p : neededSharks)
					sharks.add(jaws.getShark(p.getName()));
				break;
			case "Last Week":
				neededSharks = jaws.pastWeek();
				for (Ping p : neededSharks)
					sharks.add(jaws.getShark(p.getName()));
				break;
			case "Last Month":
				neededSharks = jaws.pastMonth();
				for (Ping p : neededSharks)
					sharks.add(jaws.getShark(p.getName()));
				break;
		}
	}

	private void filterByGender(List<Shark> sharks, String gender)
	{
		switch(gender)
		{
			case "All":
				break;
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
			case "All":
				break;
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
		filterByRange(foundSharks, range);
		filterByGender(foundSharks, gender);
		filterByStage(foundSharks, stage);
	}
}
