package Jaws.View;

import api.jaws.Location;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapFrame extends JDialog
{
	private final static String API_KEY = "AIzaSyBDwexGsiX_jxHSaii4CwIpF4A-oDGgCA0";
	private JLabel map;

	public MapFrame(JFrame parent)
	{
		super(parent, "Favourite Sharks On Map");

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		map = new JLabel();
		add(map);
	}

	public void showOnMap(HashMap<String, Location> sharksAndLocations)
	{
		URL mapLocation = null;

		String strMapLocation = "https://maps.googleapis.com/maps/api/staticmap?";
		strMapLocation += "size=640x640&";
		strMapLocation += "maptype=hybrid&";

		for (Map.Entry<String, Location> s : sharksAndLocations.entrySet())
		{
			char firstLetter = Character.toUpperCase(s.getKey().charAt(0));
			String strLatitude = Double.toString(s.getValue().getLatitude());
			String strLongitude = Double.toString(s.getValue().getLongitude());

			strMapLocation += "markers=color:red%7Clabel:" + firstLetter + "%7C" + strLatitude + "," + strLongitude + "&";
		}

		strMapLocation += "key=" + API_KEY;

		try
		{
			mapLocation = new URL(strMapLocation);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		ImageIcon mapIcon = new ImageIcon(mapLocation);
		map.setIcon(mapIcon);
		pack();
		setLocationRelativeTo(null);
	}
}
