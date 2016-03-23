package Jaws.View;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class  Statistics extends JFrame {
	Jaws jaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
	
	public Statistics(){
		super ("Statistics");
		this.setSize(1500, 500);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		widgetscreation();
		this.setVisible(true);
		
		
	}
	
	private void widgetscreation() {
		
		
		
		
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
