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
		ArrayList<String> sharkNames = jaws.getSharkNames();
		for (String s : sharkNames)
		{
			allSharks.add(jaws.getShark(s));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		ArrayList<Ping> neededSharks = new ArrayList<>();
		switch(range)
		{
			case "Last 24 hours":
				neededSharks = jaws.past24Hours();
				break;
			case "Last Week":
				neededSharks = jaws.pastWeek();
				break;
			case "Last Month":
				neededSharks = jaws.pastMonth();
				break;
		}
	}
}
