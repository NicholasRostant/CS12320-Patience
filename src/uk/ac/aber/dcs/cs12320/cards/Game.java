package uk.ac.aber.dcs.cs12320.cards;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import uk.ac.aber.dcs.cs12320.cards.gui.TheFrame;



/**
 * Contains all the main functions used to play the game including the menu and
 * its subsequent components.
 * 
 * @author Nicholas Rostant
 * @version 1.4(6th May 2015)
 */
public class Game {
	private Scanner scan;
	private Deck deck;
	private TheFrame frame;
	private ScoreCard scores;
	private ArrayList<String> playingField;
	private int topOfDeck;
	private int pilesOnField;
	private String deckFile;
	private int outputMode;

	
	/**
	 * Constructor creates an instance of the game.
	 */
	public Game() {
		scan = new Scanner(System.in);
		playingField = new ArrayList<String>();
		frame = new TheFrame();
		topOfDeck = 0;
		outputMode = 0;

		// Initialise at -1 as the first pile we create will have an index of 0
		pilesOnField = -1;
	}

	
	/**
	 * Runs a small setup menu on launch that allows the user to select the deck
	 * they will be using. Supports imported decksbut currently only with
	 * standard playing cards.
	 */
	public void initialise() {
		scan = new Scanner(System.in);
		String input = null;
		System.out.println("1 - Load default deck");
		System.out.println("2 - Load custom deck");
		System.out.println("3 - Exit");
		input = scan.next();

		switch (input) {
		case "1":
			// Use a standard deck of cards for the default case
			deckFile = "cards.txt";
			deck = new Deck(deckFile);
			scores = new ScoreCard("scores_cards.txt");
			break;

		case "2":
			StringBuilder sb = new StringBuilder();
			System.out.println("Enter deck file");
			deckFile = scan.next();
			deck = new Deck(deckFile);

			// Score files named in format "scores_FILENAME.TXT"
			sb.append("scores_");
			sb.append(deckFile);
			scores = new ScoreCard(sb.toString());
			break;

		case "3":
			System.exit(0);
			break;

		default:
			System.out.println("Try again");
		}
	}

	
	/**
	 * Reads user input and makes a selection based upon the input character,
	 * implements all menu options.
	 */
	public void runMenu() {

		// The change made variable allows us to determine if a called function
		// has updated the play area
		int changeMade = 0;
		String input = null;

		do {
			printMenu();
			System.out.println("Make a selection:");
			scan = new Scanner(System.in);
			try {
				input = scan.nextLine().toUpperCase();

				switch (input) {
				case "1":
					deck.shuffle();
					break;

				case "2":
					showPack();
					break;

				case "3":
					dealCard();
					break;

				case "4":
					changeMade = lastPileBackOne();
					if (changeMade == 0) {
						System.out.println("That move cannot be made! ");
						System.out
								.println("Either there are not enough piles available or the cards cannot stack.");
					}
					break;

				case "5":
					changeMade = lastPileBackTwo();
					if (changeMade == 0) {
						System.out.println("That move cannot be made! ");
						System.out
								.println("Either there are not enough piles available or the cards cannot stack.");
					}
					break;

				case "6":
					changeMade = amalgamate();
					if (changeMade == 0) {
						System.out.println("That move cannot be made! ");
						System.out
								.println("Either that move is invalid or the cards cannot stack.");
					}
					break;

				case "7":
					playForMe();
					break;

				case "8":
					scores.printScores();
					break;

				case "T":
					selectOutput();
					break;

				case "R":
					restart();
					break;

				case "Q":
					exit();
					break;
				default:
					System.out.println("Try again");
				}
			} catch (InputMismatchException e) {
				System.err.println("Invalid input recieved");
				System.out.println(e.getMessage());
			}

		} while (!(input.equals("Q")));

		frame.dispose();
	}

	
	/**
	 * Prints out a menu for the user to select options from.
	 */
	private void printMenu() {
		System.out.println("1 - Shuffle the deck");
		System.out.println("2 - Show pack");
		System.out.println("3 - Deal a card");
		System.out.println("4 - Move last pile to previous");
		System.out.println("5 - Move last pile back 2 spaces");
		System.out.println("6 - Move piles in the middle");
		System.out.println("7 - Play for me");
		System.out.println("8 - Show best scores");
		System.out.println("T - Toggle alternate output modes");
		System.out.println("R - Restart application");
		System.out.println("Q - Exit game");
	}

	
	/**
	 * Called by runMenu and takes input from the user on which piles they want
	 * to stack.
	 * 
	 * @return changeMade An integer that when equal to 1 indicates a change to
	 *         the play area was made.
	 */
	private int amalgamate() {
		int changeMade = 0;
		int firstIndex;
		int secondIndex;
		scan = new Scanner(System.in);

		System.out
				.println("Enter the position of the card closest to the left");
		firstIndex = scan.nextInt();

		System.out
				.println("Enter the position of the card closest to the right");
		secondIndex = scan.nextInt();

		// Indexes decremented as the array we are operating on begins at 0
		changeMade = amalgamatePiles(firstIndex - 1, secondIndex - 1);
		return changeMade;
	}

	
	/**
	 * Prints out all of the cards remaining in the deck for the user to see.
	 */
	private void showPack() {
		Card theCard;
		String cardName;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < (deck.getSize()); i++) {

			theCard = deck.getCard(i);
			cardName = theCard.toString();

			sb.append(cardName);
			sb.append(" ");

		}
		System.out.println(sb.toString());
	}

	
	/**
	 * Moves the top card from the deck onto the playing field and displays it
	 */
	private void dealCard() {
		if (topOfDeck > (deck.getSize() - 1)) {
			System.out.println("No more cards in the Deck!");
		} else {
			Card newCard;
			newCard = deck.getCard(topOfDeck);
			playingField.add(newCard.getImage());

			topOfDeck++;
			pilesOnField++;
			output();

			if (topOfDeck > (deck.getSize() - 1)) {
				frame.allDone();
			}
		}
	}

	
	/**
	 * Attempts to move the last pile on the play area onto the previous one.
	 * Updates the JFrame
	 * 
	 * @return changeMade An integer that when non-zero indicates a change to
	 *         the play area was made.
	 */
	private int lastPileBackOne() {
		int changeMade = 0;
		if (pilesOnField > 0) {

			String sourceCard;
			String destinationCard;
			Character cardVal;
			Character cardSuit;

			sourceCard = playingField.get(pilesOnField);
			destinationCard = playingField.get(pilesOnField - 1);

			// Retrieves the characters from the card file that indicate it's
			// value and its suit
			// Card strings here are in format vs.gif where v is value and s is
			// suit
			cardVal = sourceCard.charAt(0);
			cardSuit = sourceCard.charAt(1);

			// If either the value or suit are the same
			if (cardVal.equals(destinationCard.charAt(0))
					|| cardSuit.equals(destinationCard.charAt(1))) {

				playingField.remove(pilesOnField - 1);

				pilesOnField--;

				output();
				changeMade = 1;
			}
		}
		return changeMade;
	}

	
	/**
	 * Attempts to move the last pile on the play area onto the pile two spaces
	 * away on the left. Updates the JFrame.
	 * 
	 * @return changeMade An integer that when non-zero indicates a change to
	 *         the play area was made.
	 */
	private int lastPileBackTwo() {
		int changeMade = 0;
		if (pilesOnField > 2) {

			String sourceCard;
			String destinationCard;
			Character cardVal;
			Character cardSuit;

			sourceCard = playingField.get(pilesOnField);
			destinationCard = playingField.get(pilesOnField - 3);

			// Retrieves the characters from the card file that indicate it's
			// value and its suit
			// Card strings here are in format vs.gif where v is value and s is
			// suit
			cardVal = sourceCard.charAt(0);
			cardSuit = sourceCard.charAt(1);

			// If either value or suit are the same
			if (cardVal.equals(destinationCard.charAt(0))
					|| cardSuit.equals(destinationCard.charAt(1))) {
				playingField.remove(pilesOnField - 3);
				playingField.add(pilesOnField - 3, sourceCard);
				playingField.remove(pilesOnField);

				pilesOnField--;

				output();
				changeMade = 1;
			}
		}
		return changeMade;
	}

	
	/**
	 * Attempts to move the piles indicated by passed indexes atop one another.
	 * Always moves right onto left. This will only complete if the piles are
	 * both valid and either next to each other or two spaces apart. Updates the
	 * JFrame
	 * 
	 * @param firstIndex
	 *            The index of the leftmost pile involved
	 * @param secondIndex
	 *            The index of the rightmost pile involved
	 * 
	 * @return changeMade An integer that when non-zero indicates a change to
	 *         the play area was made.
	 */
	private int amalgamatePiles(int firstIndex, int secondIndex)
			throws InputMismatchException {
		int changeMade = 0;

		if (secondIndex > pilesOnField || firstIndex < 0
				|| secondIndex < firstIndex) {
			changeMade = 0;
		} else {
			if (pilesOnField > 1) {
				if (secondIndex == (firstIndex + 1)
						|| secondIndex == (firstIndex + 3)) {
					String sourceCard;
					String destinationCard;
					Character cardVal;
					Character cardSuit;

					sourceCard = playingField.get(secondIndex);
					destinationCard = playingField.get(firstIndex);

					cardVal = sourceCard.charAt(0);
					cardSuit = sourceCard.charAt(1);

					if (cardVal.equals(destinationCard.charAt(0))
							|| cardSuit.equals(destinationCard.charAt(1))) {
						playingField.remove(firstIndex);
						playingField.add(firstIndex, sourceCard);
						playingField.remove(secondIndex);

						pilesOnField--;

						output();
						changeMade = 1;
					}
				} else {
					System.out
							.println("Those piles are not in the correct positions!");
				}
			}
		}
		return changeMade;
	}

	
	/**
	 * Asks the user for the number of plays they would like made and then calls
	 * the autoPlay function an appropriate number of times.
	 * 
	 * @throws InputMismatchException
	 */
	private void playForMe() throws InputMismatchException {
		int requiredPlays;
		int changeMade;
		Scanner scan = new Scanner(System.in);

		System.out.println("How many moves would you like to be played?");
		requiredPlays = scan.nextInt();
		for (int i = 0; i < requiredPlays; i++) {
			try {
				// 0.3 second sleep to allow the display to catch up
				Thread.sleep(300);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			changeMade = autoPlay();

			if (changeMade == 0) {
				System.out.println("No more moves available!");
				break;
			}
		}
	}

	
	/**
	 * Plays a single move for the user. This function can: draw cards, move the
	 * end pile back one, move the end pile back two spaces, and amalgamate
	 * piles in the middle. It will attempt to do this from the right inwards
	 * and only draws a card if there are no other moves to be played.
	 * 
	 * @return changeMade An integer that when non-zero indicates a change to
	 *         the play area was made.
	 */
	private int autoPlay() {
		int changeMade = 0;

		changeMade = lastPileBackOne();

		if (changeMade == 0) {

			changeMade = lastPileBackTwo();

			if (changeMade == 0) {

				// Move through the playing field and attempt to amalgamate
				// piles
				for (int i = 1; i < pilesOnField; i++) {
					changeMade = amalgamatePiles(i - 1, i);
					if (changeMade == 1) {
						break;
					} else {
						changeMade = amalgamatePiles(i - 1, i + 2);
						if (changeMade == 1) {
							break;
						}
					}
				}
			}
		}
		if (changeMade == 0 && topOfDeck != (deck.getSize())) {
			dealCard();
			changeMade = 1;
		}
		return changeMade;
	}

	
	/**
	 * Presents a menu to the user allowing them to select what alternate forms
	 * of output they would like. If an option is selected which includes output
	 * to the log file the function will print a timestamp to the log file and
	 * include the type of deck being played with.
	 */
	private void selectOutput() {
		scan = new Scanner(System.in);
		System.out.println("1 - Logfile output");
		System.out.println("2 - Text output");
		System.out.println("3 - Both");
		String input = scan.next();

		switch (input) {
		case "1":
			outputMode = 1;
			try {
				// Retrieve a timestamp using the java.util.Calendar library
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(Calendar.getInstance().getTime());
				FileWriter fw = new FileWriter("log.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter outFile = new PrintWriter(bw);

				outFile.println("New Session Started at");
				outFile.println(timeStamp);
				outFile.print("Playing with deck: ");
				outFile.println(deckFile);
				outFile.close();
			} catch (IOException e) {
				System.out.println("Unable to access log.txt");
			}
			break;

		case "2":
			outputMode = 2;
			break;

		case "3":
			outputMode = 3;
			try {
				// Retrieve a timestamp using the java.util.Calendar library
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(Calendar.getInstance().getTime());
				FileWriter fw = new FileWriter("log.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter outFile = new PrintWriter(bw);

				outFile.println("New Session Started at");
				outFile.println(timeStamp);
				outFile.print("Playing with deck: ");
				outFile.println(deckFile);
				outFile.close();
			} catch (IOException e) {
				System.out.println("Unable to access log.txt");
			}
			break;

		default:
			System.out.println("Invalid input");
			break;
		}
	}

	
	/**
	 * Provides output functionality to other functions. This will update the
	 * JFrame and perform any alternate output methods outlined in the
	 * selectOutput() function.
	 */
	private void output() {

		frame.cardDisplay(playingField);

		String card;

		// Create a string containing the value and suit of the top of all piles
		// in play
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= pilesOnField; i++) {
			card = playingField.get(i);
			sb.append(card.charAt(0));
			sb.append(card.charAt(1));
			sb.append(" ");
		}

		// Print to console
		if (outputMode > 1) {

			System.out.println(sb.toString());
		}

		// Print to log file
		if (outputMode == 3 || outputMode == 1) {

			try {
				FileWriter fw = new FileWriter("log.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter outFile = new PrintWriter(bw);

				outFile.println(sb.toString());

				outFile.close();
			} catch (IOException e) {
				System.out.println("Unable to access log file");
			}
		}
	}

	
	/**
	 * Creates a new instance of the program and closes the old
	 */
	private void restart() {
		try {
			saveLow();
			// Wait a moment so the applications do not clash
			Thread.sleep(200);
		} catch (InterruptedException e) {
			System.out.println("Error ocurred while saving low scores");
		}
		
		frame.dispose();
		Game app = new Game();
		app.initialise();
		app.runMenu();
	}

	
	/**
	 * Saves the scores then exits the program
	 */
	private void exit() {
		saveLow();
		System.exit(0);
	}

	
	/**
	 * Saves the score if it is low enough
	 */
	private void saveLow() {
		if (topOfDeck == deck.getSize()) {
			scores.addScore(playingField.size());
		}
		scores.saveCard(deckFile);
	}
}
