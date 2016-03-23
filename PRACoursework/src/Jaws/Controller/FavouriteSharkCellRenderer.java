package Jaws.Controller;

import api.jaws.Jaws;
import api.jaws.Location;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public class FavouriteSharkCellRenderer extends DefaultListCellRenderer
{
	private final static Location KINGS_LOCATION = new Location(0.1161, 51.5119);
	private Jaws jaws;
	private Map<String, String> sharksDistances;

	public FavouriteSharkCellRenderer(Jaws jaws)
	{
		this.jaws = jaws;
		sharksDistances = new HashMap<String, String>();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		String sharkName = (String)value;
		if (!sharksDistances.containsKey(sharkName))
		{
			Location sharkLoc = jaws.getLastLocation(sharkName);
			double sharkDistance = calculateDistance(KINGS_LOCATION.getLatitude(), KINGS_LOCATION.getLongitude(), sharkLoc.getLatitude(), sharkLoc.getLongitude());
			String strSharkDistance = Integer.toString((int)Math.round(sharkDistance));
			sharksDistances.put(sharkName, strSharkDistance);
		}

		if (isSelected)
		{
			setBackground(Color.blue.brighter());
			setForeground(Color.white);
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setText(sharkName + " " + sharksDistances.get(sharkName) + " meters");

		return this;
	}

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
