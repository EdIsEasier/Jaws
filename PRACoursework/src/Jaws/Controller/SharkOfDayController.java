package Jaws.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import Jaws.View.SharkOfDayFrame;
import api.jaws.Jaws;
import api.jaws.Ping;

/**
 * Controls the shark of the day, checks when to update it and display it on the shark of te day frame
 * 
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class SharkOfDayController implements ActionListener{
	private ArrayList<Ping> sharks;	//hold the pings of the sharks
	private File randomShark;	//file to hold the random shark
	private Calendar date;		//date you're looking at the shark of day
	private Random rnd;			//for finding a random shark
	private String strDate;		//date as a string
	private Jaws jaws;			//jaws API

	/**
	 * Constructor, initialises the fields
	 * 
	 * @param sharks arraylist of the pings of all sharks
	 * @param jaws the Jaws API
	 */
	public SharkOfDayController(ArrayList<Ping> sharks, Jaws jaws) {
		this.sharks = sharks;
		randomShark = new File(System.getProperty("user.dir") + "\\SharkDay.txt"); //the file for the random shark
		rnd = new Random(sharks.size()); //creates an rng to the amount of sharks
		date = new GregorianCalendar();
		strDate = new SimpleDateFormat("dd/MM/yyyy").format(date.getTime()); //format the date into a string
		this.jaws = jaws;
		
	}

	public void actionPerformed(ActionEvent e) {	//when the action happens, in this case clicking the shark of the day button
		int nextShark = rnd.nextInt(sharks.size() - 1);	//find a random shark
		try {
			BufferedReader reader = new BufferedReader(new FileReader(randomShark));	//used to read the randomshark file
			String lastDate = reader.readLine();	//get the last date that the favourite shark was accessed
			String[] lastDateSplit = lastDate.split("/"); //split the date down into it's parts (day, month, year)
			int[] iTime = new int[3];
			for(int i = 0; i < 3; i++){ //convert them to integers
				iTime[i] = Integer.parseInt(lastDateSplit[i]);
			}
			Calendar calendar = new GregorianCalendar();
			calendar.set(iTime[2], iTime[1] - 1, iTime[0]); //make the last date into a calendar
			calendar.add(Calendar.DAY_OF_MONTH, 1); //increment the day by one (so we can see if it has been at least one day)
			date = calendar.getInstance();
			if(date.compareTo(calendar) >= 0){	//if the date you opened the frame is greater than the previous date (+ 1 for the day)
				String sharkName = sharks.get(nextShark).getName(); //get the shark name of the random shark
				PrintWriter writer = new PrintWriter(new FileWriter(randomShark, false));
				writer.append(strDate + "\r\n"); //append the file with the new date accessed
				writer.append(sharkName + "\r\n"); //append the file with the new random shark
				writer.close();
				SharkOfDayFrame newShark = new SharkOfDayFrame(sharkName, jaws.getVideo(sharkName)); //create and open the shark of the day frame with the new details
				newShark.setVisible(true);
			}
			else{ //if the date isn't greater
				SharkOfDayFrame newShark = new SharkOfDayFrame(reader.readLine(), jaws.getVideo(reader.readLine())); //open the shark of the day frame with the old details
				newShark.setVisible(true);
			}
			reader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
