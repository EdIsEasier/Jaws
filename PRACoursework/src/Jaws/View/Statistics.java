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
	Jaws jaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
	ArrayList<Ping> Sharks;
	ArrayList<Shark> SharksObjects;
	ArrayList<Shark> SharksLocation;

	private Jaws shark;
	public Statistics(){
		super ("Statistics");
		this.setSize(1500, 500);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		widgetscreation();
		this.setLayout(new GridLayout(1,3));
		
		
	}
	
	private void widgetscreation() {
    JPanel panel1 = new JPanel();
    panel1.add(firstPieChart("Last 24 Hours"));
    this.add(panel1);
    JPanel panel2 = new JPanel();  
    panel2.add(SecondPieChart());
     this.add(panel2);
    JPanel panel3 = new JPanel();
   
		
	}
	
	private JPanel firstPieChart(String input) {
		if(input.equals("Last 24 Hours")){
			Sharks = jaws.past24Hours();	
		}
		else if(input.equals("Last Week")){
			Sharks = jaws.pastWeek();
		}else{
			Sharks = jaws.pastMonth();
			
		}
		ArrayList<Shark> maleSharks = new ArrayList<Shark>();
		ArrayList<Shark> femaleSharks = new ArrayList<Shark>();
		SharksObjects = new ArrayList<Shark>();
			
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
		DefaultPieDataset dataset = new DefaultPieDataset( );
		dataset.setValue( "Male",maleSharks.size());
	    dataset.setValue( "Female",femaleSharks.size()); 
	    JFreeChart chart = ChartFactory.createPieChart(
	    "Gender Shark", dataset,true,true,false);
	    JFreeChart chart2 =chart;
	    return new ChartPanel( chart2 );
		
	}
	
	
	

	private void chart3() {
		
		
	}

	private void chart2() {
		
		
	}

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
