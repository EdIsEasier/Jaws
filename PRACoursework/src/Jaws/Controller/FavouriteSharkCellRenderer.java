package Jaws.Controller;

import api.jaws.Shark;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class FavouriteSharkCellRenderer extends JLabel implements ListCellRenderer
{


	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		Shark shark = (Shark)value;

		if (isSelected)
		{
			setBackground(Color.blue);
			setForeground(Color.red);
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		String nameAndDistance = shark.getName();

		setText(nameAndDistance);
		setFont(list.getFont());

		return this;
	}
}
