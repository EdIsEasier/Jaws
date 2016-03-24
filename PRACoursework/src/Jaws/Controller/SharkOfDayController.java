package Jaws.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import Jaws.View.SharkOfDayFrame;
import api.jaws.Jaws;
import api.jaws.Ping;

public class SharkOfDayController implements ActionListener{
	private ArrayList<Ping> sharks;
	private File randomShark;
	private Calendar date;
	private Random rnd;
	private String strDate;
	private Jaws jaws;

	public SharkOfDayController(ArrayList<Ping> sharks, Jaws jaws) {
		this.sharks = sharks;
		randomShark = new File(System.getProperty("user.dir") + "\\SharkDay.txt");
		rnd = new Random(sharks.size());
		date = Calendar.getInstance();
		strDate = new SimpleDateFormat("dd/MM/yyyy").format(date.getTime());
		this.jaws = jaws;
		
	}

	public void actionPerformed(ActionEvent e) {
		int nextShark = rnd.nextInt(sharks.size() - 1);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(randomShark));
			String lastDate = reader.readLine();
			String[] lastDateSplit = lastDate.split("/");
			int[] iTime = new int[3];
			for(int i = 0; i < 3; i++){
				iTime[i] = Integer.parseInt(lastDateSplit[i]);
			}
			Calendar calendar = new GregorianCalendar();
			calendar.set(iTime[2], iTime[1] - 1, iTime[0]);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if(date.compareTo(calendar) > 0){
				String sharkName = sharks.get(nextShark).getName();
				PrintWriter writer = new PrintWriter(new FileWriter(randomShark, false));
				writer.append(strDate + "\r\n");
				writer.append(sharkName + "\r\n");
				writer.close();
				SharkOfDayFrame newShark = new SharkOfDayFrame(sharkName, jaws.getVideo(sharkName));
				newShark.setVisible(true);
			}
			else{
				SharkOfDayFrame newShark = new SharkOfDayFrame(reader.readLine(), jaws.getVideo(reader.readLine()));
				newShark.setVisible(true);
			}
			reader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
