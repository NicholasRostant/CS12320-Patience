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

/**
 * A class that contains an ArrayList of scores and allows us to read in scores
 * from a file and to save them. It can put the scores in order and can
 * determine if a score is worthy of saving.
 * 
 * @author Nicholas Rostant
 * @version 1.3(4th May 2015)
 */
public class ScoreCard {

	private ArrayList<Score> scores;
	private int cardLength;

	/**
	 * Constructor reads in a provided file of scores based on the deck being
	 * used.
	 * 
	 * @param fileName
	 *            the filename of the deck
	 */
	ScoreCard(String fileName) {
		scores = new ArrayList<Score>();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			Scanner scoreFile = new Scanner(br);

			cardLength = scoreFile.nextInt();

			String nameIn;
			int scoreIn;
			for (int i = 0; i < cardLength; i++) {
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

	/**
	 * Saves the score card to a file with format "scores_FILENAME.TXT"
	 * 
	 * @param fileName
	 *            the filename of the deck being used
	 */
	public void saveCard(String fileName) {
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

			for (int i = 0; i < cardLength; i++) {
				outTemp = scores.get(i);
				outName = outTemp.getName();
				outScore = outTemp.getScore();

				outFile.println(outName);
				outFile.println(outScore);
			}
			outFile.close();
		} catch (IOException e) {
			System.out
					.println("Unexpected error! The score file for this deck is missing or corrupt");
		}
	}

	/**
	 * Sorts the existing scores in order lowest first
	 */
	private void sort() {
		Collections.sort(scores, new ScoreComparator());
	}

	/**
	 * Prints the scores to the console
	 */
	public void printScores() {
		Score tempScore;
		for (int i = 0; i < cardLength; i++) {
			tempScore = scores.get(i);
			System.out.println(tempScore.toString());
		}
	}

	/**
	 * Determines if the score provided is low enough to join the leaderboard
	 * and if so will add it and if the board is full will remove the worst
	 * score
	 * 
	 * @param newScore
	 *            score attained
	 */
	public void addScore(int newScore) {
		Score tempScore;
		String tempName;
		Scanner in = new Scanner(System.in);

		// If the board is full will check to see if qualifies then will remove
		// the worst
		// else will simply add the score on
		if (cardLength > 9) {
			tempScore = scores.get(cardLength - 1);
			if (tempScore.getScore() > newScore) {
				System.out
						.println("You have qualified for a low score! Enter your name:");
				tempName = in.nextLine();
				scores.remove(tempScore);
				tempScore = new Score(tempName, newScore);
				scores.add(tempScore);
			}
		} else {
			System.out
					.println("You have qualified for a low score! Enter your name:");
			tempName = in.nextLine();
			cardLength++;
			tempScore = new Score(tempName, newScore);
			scores.add(tempScore);
		}
		sort();
		printScores();

	}
}
