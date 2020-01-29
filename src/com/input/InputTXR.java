package com.input;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class InputTXR {

	File inFile;

	boolean firstWrite;

	String patientName;
	String ID;
	String hookupTime;

	String[] labels = { "Name", "ID", "Hookup Time", "Hour", "Minute",
			"Systolic", "Diastolic", "MAP", "PP", "HR" };

	BufferedReader br;

	ArrayList<String[]> fullData = new ArrayList<String[]>();
	String[][] finalOutput = new String[112][8];

	int numberErrors;

	public InputTXR(File f, boolean b) {
		inFile = f;
		firstWrite = b;

		try {
			getContents();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 50; i++) {
			removeErrors();
		}
		generateTime();
		if(fullData.size() != 0){
			prime24Hour();
			fitTimes();
			printFinalOutput();
		}
	}

	private void getContents() throws IOException {
		try {
			br = new BufferedReader(new FileReader(inFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String totalInput = "";
		String st;
		while ((st = br.readLine()) != null) {
			totalInput += st;
			totalInput += "\n";
		}

		br.close();

		ArrayList<Character> inputAsChar = new ArrayList<>();
		for(char c : totalInput.toCharArray()){
			inputAsChar.add(c);
		}

		inputAsChar.removeAll(Collections.singleton('\u0000'));

		String normalizedInput = "";
		for(Character c : inputAsChar){
			normalizedInput += ""+c.toString();
		}

		ArrayList<String> lines = new ArrayList<>();
		for(String s : normalizedInput.split(System.getProperty("line.separator"))){
			lines.add(s);
		}

		patientName = lines.get(1);
		String[] patientSplit = patientName.split(",");
		patientName = patientSplit[0] + "; " + patientSplit[1];
		String[] patientSplit2 = patientName.split(":");
		patientName = patientSplit2[1];

		ID = lines.get(2);
		String[] IDSplit = ID.split(":");
		ID = IDSplit[1];

		hookupTime = lines.get(3);
		String[] hookupSplit = hookupTime.split(":");
		hookupTime = hookupSplit[1];

		for(int i = 6; i<lines.size(); i++){
			String temp = lines.get(i);
			String[] tempArray = temp.split(",");
			fullData.add(tempArray);
		}
	}

	private void removeErrors() {

		for (int i = 0; i < fullData.size(); i++) {
			if (fullData.get(i)[fullData.get(i).length - 1].equals("EE-2")) {
				fullData.remove(i);
				numberErrors++;
			}
		}
	}

	public void print() {
		for (int i = 0; i < fullData.size(); i++) {
			for (int j = 0; j < fullData.get(i).length; j++) {
				System.out.println(fullData.get(i)[j]);
			}
			System.out.println("**");
		}
	}

	private void generateTime() {
		gen genTime = new gen();
		String[] temp = genTime.getTimes();

		for (int i = 0; i < temp.length; i++) {
			finalOutput[i][0] = temp[i];
		}

	}

	public void printFinalOutput() {
		for (int i = 0; i < finalOutput.length; i++) {
			for (int j = 0; j < finalOutput[i].length; j++) {
				System.out.println(finalOutput[i][j]);
			}
			System.out.println("**");
		}
	}

	private void prime24Hour() {

		int day = 0;

		if (fullData.size() == 0) {
			System.out.println("Empty.");
		} else {
			day = Integer.parseInt(fullData.get(0)[3]);
		}

		for (int i = 0; i < fullData.size(); i++) {
			int tempDay = Integer.parseInt(fullData.get(i)[3]);
			if (tempDay != day) {
				int a = Integer.parseInt(fullData.get(i)[5]);
				a += 24;
				fullData.get(i)[5] = "" + a;
			}
		}
	}

	public void makeHeadingsFullDir() {
		String output = inFile.getParent().toString() + "FORMATTED OUTPUT.csv";
		File out = new File(output);
		FileWriter a;
		try {
			out.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			a = new FileWriter(out, true);
			if (firstWrite == true) {
				for (int i = 0; i < 3; i++) {
					a.write(labels[i]);
					a.write(",");
				}
			

			for (int i = 0; i < finalOutput.length; i++) {
				for (int j = 3; j < labels.length; j++) {
					a.write(finalOutput[i][0] + "_" + labels[j]);
					a.write(",");
				}
			}
			}
			a.write("\n");
			
			a.write(patientName + ",");
			a.write(ID + ",");
			a.write(hookupTime + ",");

			for (int i = 0; i < finalOutput.length; i++) {
				for (int j = 1; j < 8; j++) {
					if (finalOutput[i][j] == null) {
						a.write(" ");
					} else {
						a.write(finalOutput[i][j]);
					}
					a.write(",");
				}
			}

			a.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void makeHeadings() {
		String output = inFile.toString() + " FORMATTED OUTPUT.csv";
		File out = new File(output);
		FileWriter a;
		try {
			out.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			a = new FileWriter(out);
			for (int i = 0; i < 3; i++) {
				a.write(labels[i]);
				a.write(",");
			}

			for (int i = 0; i < finalOutput.length; i++) {
				for (int j = 3; j < labels.length; j++) {
					a.write(finalOutput[i][0] + "_" + labels[j]);
					a.write(",");
				}
			}

			a.write("\n");

			a.write(patientName + ",");
			a.write(ID + ",");
			a.write(hookupTime + ",");

			for (int i = 0; i < finalOutput.length; i++) {
				for (int j = 1; j < 8; j++) {
					if (finalOutput[i][j] == null) {
						a.write(" ");
					} else {
						a.write(finalOutput[i][j]);
					}
					a.write(",");
				}
			}

			a.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fitTimes() {
		for (int j = 0; j < fullData.size(); j++) {
			int checkHour = Integer.parseInt(fullData.get(j)[5]);
			int checkMin = Integer.parseInt(fullData.get(j)[6]);

			// System.out.println(checkHour);
			// System.out.println(checkMin);

			for (int i = 0; i < finalOutput.length; i++) {
				String[] temp = finalOutput[i][0].split("\\.");
				int hour = Integer.parseInt(temp[0]);
				int min = Integer.parseInt(temp[1]);

				if (i + 1 < finalOutput.length) {
					String[] temp2 = finalOutput[i + 1][0].split("\\.");
					int hourN = Integer.parseInt(temp2[0]);
					int minN = Integer.parseInt(temp2[1]);

					if (checkHour == hour) {
						if (checkMin >= min && checkMin < minN) {
							for (int k = 1; k < 8; k++) {
								finalOutput[i][k] = fullData.get(j)[k + 4];
							}
						}
					}
				}

			}
		}
	}
}
