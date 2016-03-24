package Jaws.View;

import api.jaws.Location;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFrame extends JDialog
{
	private final static String API_KEY = "AIzaSyBDwexGsiX_jxHSaii4CwIpF4A-oDGgCA0";
	private JLabel map;
	private List<String> sharknadoSharks;

	public MapFrame(JFrame parent)
	{
		super(parent, "Favourite Sharks On Map");

		sharknadoSharks = new ArrayList<>();
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
			checkForSharknado(s.getKey(), strLatitude, strLongitude);

			strMapLocation += "markers=color:red%7Clabel:" + firstLetter + "%7C" + strLatitude + "," + strLongitude + "&";
		}

		strMapLocation += "key=" + API_KEY;

		if(sharknadoSharks.size() > 0)
			((Favourites)getParent()).sharknadoWarning(sharknadoSharks);

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

	private void checkForSharknado(String sharkName, String latitude, String longitude)
	{
		URL mapLocation = null;

		String strMapLocation = "https://maps.googleapis.com/maps/api/staticmap?";
		strMapLocation += "size=1x1&";
		strMapLocation += "maptype=roadmap&";

		strMapLocation += "center=" + latitude + "," + longitude + "&";
		strMapLocation += "key=" + API_KEY;

		System.out.println(strMapLocation);

		try
		{
			mapLocation = new URL(strMapLocation);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		BufferedImage mapImage = null;

		try
		{
			mapImage = ImageIO.read(mapLocation);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		int[] dataBuffInt = mapImage.getRGB(0, 0, mapImage.getWidth(), mapImage.getHeight(), null, 0, mapImage.getWidth());

		Color c = new Color(dataBuffInt[0]);

		System.out.println(c.getRed());
		System.out.println(c.getGreen());
		System.out.println(c.getBlue());

		if(c.getBlue() < 210)
		{
			System.out.println("Sharknado!");
			sharknadoSharks.add(sharkName);
		}
	}
}
