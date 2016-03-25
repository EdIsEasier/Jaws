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

/**
 * MapFrame class is a simple window used to show a map image
 * provided by the Google Static Maps API
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class MapFrame extends JDialog
{
	private final static String API_KEY = "AIzaSyBDwexGsiX_jxHSaii4CwIpF4A-oDGgCA0"; // Google Static Maps API key
	private JLabel map; // JLabel which will be used to show the map
	private List<String> sharknadoSharks; // sharks that have been detected on land

	/**
	 * Constructor that initialises the sharknado shark list and sets
	 * up the window
	 *
	 * @param parent dialog's parent
	 */
	public MapFrame(JFrame parent)
	{
		super(parent, "Favourite Sharks On Map");

		sharknadoSharks = new ArrayList<>();
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		map = new JLabel();
		add(map);
	}

	/**
	 * Takes a hash map of shark names and locations and uses them to call
	 * the Google Static Maps API to retrieve an image of all the sharks
	 * pinned on the map to their respective locations
	 *
	 * @param sharksAndLocations shark names and their locations
	 */
	public void showOnMap(HashMap<String, Location> sharksAndLocations)
	{
		URL mapLocation = null; // initialise a URL

		String strMapLocation = "https://maps.googleapis.com/maps/api/staticmap?"; // start the URL
		strMapLocation += "size=640x640&"; // set the map's size
		if (sharksAndLocations.size() < 2)
			strMapLocation += "zoom=7&"; // if there's only one shark we want to zoom out a little bit to see where it is
		strMapLocation += "maptype=hybrid&"; // set the map type (hybrid shows a satellite map and roads)

		for (Map.Entry<String, Location> s : sharksAndLocations.entrySet()) // for each shark and its location
		{
			// get the first letter of the shark's name and make it uppercase (this will be used for map markers to know which
			// marker refers to which shark's location)
			char firstLetter = Character.toUpperCase(s.getKey().charAt(0));
			String strLatitude = Double.toString(s.getValue().getLatitude()); // convert latitude and longitude to strings
			String strLongitude = Double.toString(s.getValue().getLongitude());
			checkForSharknado(s.getKey(), strLatitude, strLongitude); // check if the current shark is on land

			// create a marker for each shark that will have its name's first letter and put it exactly where the shark is
			strMapLocation += "markers=color:red%7Clabel:" + firstLetter + "%7C" + strLatitude + "," + strLongitude + "&";
		}

		strMapLocation += "key=" + API_KEY; // finish the url by adding the API key

		// if any of the shark were found on land
		if(sharknadoSharks.size() > 0)
			((Favourites)getParent()).sharknadoWarning(sharknadoSharks); // get the Favourites window to create a Sharknado warning

		try
		{
			mapLocation = new URL(strMapLocation); // set the URL to our newly generated Google Static Maps API address
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		ImageIcon mapIcon = new ImageIcon(mapLocation); // create an ImageIcon that uses the image from the URL
		map.setIcon(mapIcon); // set the JLabel's icon to the image
		pack();
		setLocationRelativeTo(null); // center the window
	}

	/**
	 * Checks if given shark is on land
	 *
	 * @param sharkName name of the shark
	 * @param latitude latitude of where the shark is
	 * @param longitude longitude of where the shark is
	 */
	private void checkForSharknado(String sharkName, String latitude, String longitude)
	{
		URL mapLocation = null; // new URL

		String strMapLocation = "https://maps.googleapis.com/maps/api/staticmap?"; // start the URL
		strMapLocation += "size=1x1&"; // request a 1x1 size image
		strMapLocation += "maptype=roadmap&"; // set the map type to roadmap, because water is easier to detect as it is usually only one shade of blue

		strMapLocation += "center=" + latitude + "," + longitude + "&"; // center the map exactly where the shark is
		strMapLocation += "key=" + API_KEY; // finish the URL with the API key

		try
		{
			mapLocation = new URL(strMapLocation); // set the URL to our newly generated Google Static Maps API address
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		BufferedImage mapImage = null; // new BufferedImage

		try
		{
			mapImage = ImageIO.read(mapLocation); // set the BufferedImage to our map image
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// get the RGB values of our 1x1 map picture
		int[] dataBuffInt = mapImage.getRGB(0, 0, mapImage.getWidth(), mapImage.getHeight(), null, 0, mapImage.getWidth());

		// add the RGB values to a new Color object
		Color c = new Color(dataBuffInt[0]);

		if(c.getBlue() < 210) // if the amount of blue in the picture is less than 210
		{
			// the shark must be on land
			sharknadoSharks.add(sharkName);
		}
	}
}
