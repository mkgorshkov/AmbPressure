package com.input;

public class gen {
	
	String[] totalTimes = new String[112];
	
	public gen(){
		generateTimes();
	}
	
	private void generateTimes(){
		int hour = 8;
		int minute = 0;
		int count = 0;
				
		while (hour < 48) {

			if (hour > 22 && hour < 31) {
				minute += 30;
			}
			
			else {
				minute += 20;
				
			}
			if(minute == 60){
				hour++;
				minute = 0;
			}
			
			totalTimes[count] = hour+"."+minute;
			count++;
		}
		
	}
	
	public String[] getTimes(){
		return totalTimes.clone();
	}
	
}
