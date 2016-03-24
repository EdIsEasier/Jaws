package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Jaws.Controller.FavouriteSharkCellRenderer;
import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Ping;

public class Favourites extends JFrame{

	private File loggedIn;
	private JList jlSharks;
	private JButton jbMap;
	private Jaws jaws;
	private SearchFrame search;
	private DefaultListModel<String> favouriteSharksModel;
	private String path;
	private ArrayList<Ping> sharkPings;
	private JPanel jpRightPanel;
	private boolean sharknado;
	private JTextArea warning;

	public Favourites(Jaws jaws){
		super("Favourites");
		path = System.getProperty("user.dir") + "\\Users\\";
		favouriteSharksModel = new DefaultListModel<>();
		warning = new JTextArea();
		sharknado = false;
		this.jaws = jaws;
		loggedIn = new File(path + "Default.txt");
		addComponentListener(new sharkListListener());
		createWidgets();
	}

	private void createWidgets() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel jlText = new JLabel("Your favourite sharks are this far away from you : ");
		jpRightPanel = new JPanel(new BorderLayout());
		jbMap = new JButton("Show on Map");
		jpRightPanel.add(jbMap, BorderLayout.NORTH);
		jbMap.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] sharksArr = new String[favouriteSharksModel.toArray().length];
				System.arraycopy(favouriteSharksModel.toArray(), 0, sharksArr, 0, favouriteSharksModel.toArray().length);
				ArrayList<String> sharks = new ArrayList<String>(Arrays.asList(sharksArr));
				HashMap<String, Location> sharksLocs = new HashMap<String, Location>();

				for(String s : sharks)
					sharksLocs.put(s, jaws.getLastLocation(s));

				MapFrame map = new MapFrame(Favourites.this);
				map.showOnMap(sharksLocs);
				map.setVisible(true);
			}
		});

		jlSharks = new JList(favouriteSharksModel);
		jlSharks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlSharks.setCellRenderer(new FavouriteSharkCellRenderer(jaws));
		jlSharks.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(isActive()){
					String selectedShark = jlSharks.getSelectedValue().toString();

					for(Ping p : sharkPings)
					{
						if(p.getName().equals(selectedShark))
						{
							search.clearJpAllDetails();
							search.putDescription(p.getName());
							search.setVisible(true);
							search.toFront();
							break;
						}
					}
				}
			}
		});

		add(jpRightPanel, BorderLayout.EAST);
		add(jlText,BorderLayout.NORTH);
		add(jlSharks, BorderLayout.CENTER);
		
		pack();
	}

	public void addShark(String sharkName)
	{
		favouriteSharksModel.addElement(sharkName);
		pack();
	}
	
	public void addUserFavourites(){
		if(loggedIn != null){
			try {
				String currentShark;
				BufferedReader reader = new BufferedReader(new FileReader(loggedIn));
				while((currentShark = reader.readLine()) != null){
					addShark(currentShark);
					search.switchToFollowing(currentShark);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void unfollowShark(String sharkName)
	{
		favouriteSharksModel.removeElement(sharkName);
		search.switchToFollow(sharkName);
	}

	public void switchUser(File user){
		try {
			String currentShark;
			BufferedReader reader = new BufferedReader(new FileReader(loggedIn));
			while((currentShark = reader.readLine()) != null){
				search.switchToFollow(currentShark);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loggedIn = user;
		favouriteSharksModel.clear();
		addUserFavourites();
		
	}
	
	public File getUser(){
		return loggedIn;
	}

	public void updateSharks(ArrayList<Ping> pings)
	{
		this.sharkPings = pings;
	}

	public void setSearchFrame(SearchFrame search)
	{
		this.search = search;
	}

	public void sharknadoWarning(List<String> sharkNames)
	{
		if (!sharknado)
		{
			warning = new JTextArea();
			warning.setForeground(Color.RED);
			warning.setEditable(false);
			warning.setBackground(this.getBackground());
			warning.setLineWrap(true);
			warning.setWrapStyleWord(true);
			String strWarning = "Sharknado! ";

			if(sharkNames.size() > 1)
			{
				for(String s : sharkNames)
				{
					strWarning += s + ", ";
				}

				strWarning += " are on land!";
			}
			else
			{
				for(String s : sharkNames)
				{
					strWarning += s;
				}

				strWarning += " is on land!";
			}

			warning.setText(strWarning);
			jpRightPanel.add(warning, BorderLayout.CENTER);
			pack();
			sharknado = true;
		}
	}

	public void removeSharknadoWarning()
	{
		if (warning != null)
		{
			jpRightPanel.remove(warning);
			sharknado = false;
		}
	}

	private class sharkListListener implements ComponentListener
	{

		@Override
		public void componentShown(ComponentEvent e)
		{
			if(jlSharks.getModel().getSize() != 0)
				jbMap.setEnabled(true);
			else
				jbMap.setEnabled(false);
		}

		@Override
		public void componentResized(ComponentEvent e)
		{}

		@Override
		public void componentMoved(ComponentEvent e)
		{}

		@Override
		public void componentHidden(ComponentEvent e)
		{}
	}
}
