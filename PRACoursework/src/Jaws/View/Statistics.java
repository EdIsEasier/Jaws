package Jaws.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class  Statistics extends JFrame {
	Jaws jaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");// Access jaws 
	ArrayList<Ping> Sharks;// Created a new Arraylist field for the pings
	ArrayList<Shark> SharksObjects;
	ArrayList<Shark> SharksLocation;

	private Jaws shark;
	/**
	 * This is a constructor which contains the name of my JFrame as well as its size and the creation of the components
	 */
	public Statistics(){
		super ("Statistics");
		this.setSize(1500, 500);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		widgetscreation();
		this.setLayout(new GridLayout(1,3));
		
		
	}
	/**
	 * This is where I created the 3 frames for my pie charts
	 */
	private void widgetscreation() {
    JPanel panel1 = new JPanel();
    panel1.add(firstPieChart("Last 24 Hours"));
    this.add(panel1);
    JPanel panel2 = new JPanel();  
    panel2.add(SecondPieChart());
     this.add(panel2);
    JPanel panel3 = new JPanel();
   
		
	}
	/**
	 * this  is where I created my first pie chart 
	 * @param input
	 * @return
	 */
	private JPanel firstPieChart(String input) {
		if(input.equals("Last 24 Hours")){// so if the case is 24 hours then it will get 24 hours sharks
			Sharks = jaws.past24Hours(); 
		}
		else if(input.equals("Last Week")){
			Sharks = jaws.pastWeek();// so if the case is 24 hours then it will get past week sharks
		}else{
			Sharks = jaws.pastMonth();// so if the case is 24 hours then it will get past month sharks
			
		}
		// I created ArrayLists for the two conditions
		ArrayList<Shark> maleSharks = new ArrayList<Shark>();
		ArrayList<Shark> femaleSharks = new ArrayList<Shark>();
		SharksObjects = new ArrayList<Shark>();
		// this will get the shark object 
			
		for(Ping ping : Sharks){
			SharksObjects.add(jaws.getShark(ping.getName()));
		
		}
		for(Shark shark : SharksObjects){
			if("Male".equals(shark.getGender())){
				maleSharks.add(shark);
				
			}else{
				femaleSharks.add(shark);
			}
			
		}
		
		// the pie chart data will be created 
		DefaultPieDataset dataset = new DefaultPieDataset( );
		dataset.setValue( "Male",maleSharks.size());
	    dataset.setValue( "Female",femaleSharks.size()); 
	    JFreeChart chart = ChartFactory.createPieChart(
	    "Gender Shark", dataset,true,true,false);
	    JFreeChart chart2 =chart;
	    return new ChartPanel( chart2 );
		
	}
	
//Duplicates are removed 

	private HashSet<Shark> chart1(String input) {
		ArrayList<Ping> pingwanted;
		HashSet<Shark> Shark = new HashSet<>();
		if(input.equals("Last 24 Hours")){
			pingwanted = jaws.past24Hours();
			for(Ping ping : pingwanted){
				Shark.add(jaws.getShark(ping.getName()));
				
			}
	
			
			
		}
		else if(input.equals("Last Week")){
			pingwanted = jaws.pastWeek();
			for(Ping ping : pingwanted){
				Shark.add(jaws.getShark(ping.getName()));
				
			}
			
		}
		else{
			pingwanted = jaws.pastMonth();
			for(Ping ping : pingwanted){
				Shark.add(jaws.getShark(ping.getName()));
				
			}
			
		}
		return Shark;
		
		
	}
	private JPanel SecondPieChart() {
		ArrayList<Shark> mature = new ArrayList<Shark>();
		ArrayList<Shark> immature = new ArrayList<Shark>();
		ArrayList<Shark> undetermined = new ArrayList<Shark>();
		for(Shark shark : SharksObjects){
			if("Mature".equals(shark.getStageOfLife())){
				mature.add(shark);
				
			}else if("Immature".equals(shark.getStageOfLife())){
				immature.add(shark);
			}else{
				undetermined.add(shark);
			}
		}	
		
	DefaultPieDataset dataset = new DefaultPieDataset( );
	dataset.setValue( "Mature",mature.size());
    dataset.setValue( "Immature",immature.size()); 
    dataset.setValue( "Undetermined",undetermined.size()); 
    JFreeChart chart = ChartFactory.createPieChart(
    "Stage Of Life", dataset,true,true,false);
    JFreeChart chart2 =chart;
    return new ChartPanel( chart2 );
			
		}
	private JPanel ThirdPieChart() {

	}
	public static void main(String[] args) {
		Statistics sta = new Statistics();
		sta.setVisible(true);
		

	}

}
