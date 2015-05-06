package uk.ac.aber.dcs.cs12320.cards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreCard {
	
	private ArrayList<Score> scores;
	private int cardLength;
	
	ScoreCard(String fileName){
		scores = new ArrayList<Score>();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			Scanner scoreFile = new Scanner(br);
			
			cardLength = scoreFile.nextInt();

			String nameIn;
			int scoreIn;
			for (int i = 0; i < cardLength; i++){
				nameIn = scoreFile.next();
				scoreIn = scoreFile.nextInt();
	
				Score inScore = new Score(nameIn, scoreIn);
				
				scores.add(inScore);	
			}
			sort();
			
			scoreFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unexpected Error, file is missing or corrupt");
		}
	}
	
	public void saveCard(String fileName){
		StringBuilder sb = new StringBuilder();
		sb.append("scores_");
		sb.append(fileName);
		
		fileName = sb.toString();
		try {
			sort();
			Score outTemp;
			int outScore;
			String outName;
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter outFile = new PrintWriter(bw);

			
			outFile.println(cardLength);
			
			for (int i = 0; i < cardLength; i++){
				outTemp = scores.get(i);
				outName = outTemp.getName();
				outScore = outTemp.getScore();
				
				outFile.println(outName);
				outFile.println(outScore);
			}
		outFile.close();
		} catch (IOException e) {
			System.out.println("Unexpected error! The score file for this deck is missing or corrupt");
		}
		
		
	}
	private void sort(){
		Collections.sort(scores, new ScoreComparator());
	}
	
	public void printScores(){
		Score tempScore;
		for (int i = 0; i < cardLength; i++){
			tempScore = scores.get(i);
			System.out.println(tempScore.toString());
		}
	}
	
	public void addScore(int newScore){
		Score tempScore;
		String tempName;
		Scanner in = new Scanner(System.in);
		if (cardLength > 9){
			tempScore = scores.get(cardLength);
			if (tempScore.getScore() > newScore){
				System.out.println("You have qualified for a low score! Enter your name:");
				tempName = in.nextLine();
				scores.remove(tempScore);
				tempScore = new Score(tempName, cardLength);
				scores.add(tempScore);
			}
		} else {
			System.out.println("You have qualified for a low score! Enter your name:");
			tempName = in.nextLine();
			cardLength++;
			tempScore = new Score(tempName, cardLength);
			scores.add(tempScore);
		}
		
		sort();
		printScores();
		
	}
	
	
}
