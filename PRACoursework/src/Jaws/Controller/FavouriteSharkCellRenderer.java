package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Location;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * FavouriteSharkCellRenderer class displays sharks and their distances
 * from KCL in the JList
 *
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class FavouriteSharkCellRenderer extends DefaultListCellRenderer
{
	private final static Location KINGS_LOCATION = new Location(0.1161, 51.5119); // KCL longitude and latitude
	private Jaws jaws; // reference to the Jaws API
	private Map<String, String> sharksDistances; // map of shark names and their distances for cached access

	/**
	 * Constructor that initialises the Jaws API and the sharks and distances hash map
	 *
	 * @param jaws reference to the Jaws API
	 */
	public FavouriteSharkCellRenderer(Jaws jaws)
	{
		this.jaws = jaws;
		sharksDistances = new HashMap<String, String>();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		String sharkName = (String)value; // get the selected shark's name
		if (!sharksDistances.containsKey(sharkName)) // if this shark has never been selected before
		{
			Location sharkLoc = jaws.getLastLocation(sharkName); // get the last location of the shark
			// calculate the distance from KCL
			double sharkDistance = calculateDistance(KINGS_LOCATION.getLatitude(), KINGS_LOCATION.getLongitude(), sharkLoc.getLatitude(), sharkLoc.getLongitude());
			String strSharkDistance = Integer.toString((int)Math.round(sharkDistance)); // save the distance as a string
			sharksDistances.put(sharkName, strSharkDistance); // add the shark and its distance to the hash map
		}

		if (isSelected) // if the shark is selected
		{
			setBackground(Color.blue.brighter()); // make the selection background blue
			setForeground(Color.white); // and the text white
		}
		else // if the shark is not being selected, set the default colours
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		// set the selection text to the selected shark's name and its distance from KCL
		setText(sharkName + " " + sharksDistances.get(sharkName) + " meters");

		return this;
	}

	/**
	 * Function that calculates the distance from one latitude-longitude pair
	 * to another in meters
	 *
	 * @param lat1 source latitude
	 * @param long1 source longitude
	 * @param lat2 destination latitude
	 * @param long2 destination longitude
	 * @return double distance in meters
	 */
	private double calculateDistance(double lat1, double long1, double lat2, double long2) {
		double earthRadius = 6371000; // in meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(long2 - long1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (earthRadius * c);
	}
}
