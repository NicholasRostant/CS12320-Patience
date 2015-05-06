package uk.ac.aber.dcs.cs12320.cards;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;


import uk.ac.aber.dcs.cs12320.cards.gui.TheFrame;

public class Game {
	private Scanner scan;
	private Deck deck;
	private TheFrame frame;
	private ArrayList<String> playingField;
	private int topOfDeck;
	private int pilesOnField;
	
	public Game(){
		scan = new Scanner(System.in);
		playingField = new ArrayList <String>();
		frame = new TheFrame();
		topOfDeck = 0;
		
		//Initialise at -1 as the first pile we create will have an index of 0
		pilesOnField = -1;
		
	}
	
	public void initialise(){
		String input = null;
		System.out.println("1 - Load default deck");
		System.out.println("2 - Load custom deck");
		System.out.println("3 - Exit");
		input = scan.next();
		
		switch (input){
		case "1":
			deck = new Deck("cards.txt");
			break;
		case "2":
			System.out.println("Enter deck file");
			input = scan.next();
			deck = new Deck(input);
			break;
		case "3":
			System.exit(0);
			break;
		default:
			System.out.println("Try again");
		}
	}
	
	public void runMenu(){
		int changeMade = 0;
		String input = null;
		do{
			printMenu();
			System.out.println("Make a selection:");
			scan = new Scanner(System.in);
			try{
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
					if (changeMade == 0){
						System.out.println("That move cannot be made! ");
						System.out.println("Either there are not enough piles available or the cards cannot stack.");
					}
					break;
				case "5":
					changeMade = lastPileBackTwo();
					if (changeMade == 0){
						System.out.println("That move cannot be made! ");
						System.out.println("Either there are not enough piles available or the cards cannot stack.");
					}
					break;
				case "6":
					changeMade = amalgamate();
					if (changeMade == 0){
						System.out.println("That move cannot be made! ");
						System.out.println("Either that move is invalid or the cards cannot stack.");
					}
					break;
				case "7":
					playForMe();
					break;
				case "R":
					restart();
					break;
				case "Q":
					System.exit(0);
					break;
				default:
					System.out.println("Try again");
				} 
			} catch (InputMismatchException e){
				System.err.println("Invalid input recieved");
				System.out.println(e.getMessage());
			}
					
			}while (!(input.equals("Q")));
			
			frame.dispose();
	}
	
	private void printMenu(){
		System.out.println("1 - Shuffle the deck");
		System.out.println("2 - Show pack");
		System.out.println("3 - Deal a card");
		System.out.println("4 - Move last pile to previous");
		System.out.println("5 - Move last pile back 2 spaces");
		System.out.println("6 - Move piles in the middle");
		System.out.println("7 - Play for me");
		System.out.println("8 - Show best scores");
		System.out.println("R - Restart application");
		System.out.println("Q - Exit game");
	}
	
	private int amalgamate(){
		int changeMade = 0;
		int firstIndex;
		int secondIndex;
		scan = new Scanner(System.in);
		
		System.out.println("Enter the position of the card closest to the left");
		firstIndex = scan.nextInt();
		
		System.out.println("Enter the position of the card closest to the right");
		secondIndex = scan.nextInt();
		
		changeMade = amalgamatePiles(firstIndex - 1, secondIndex - 1);
		return changeMade;
	}
	
	private void showPack() {
		Card theCard;
		String cardName;
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<(deck.getSize()); i++){

			theCard = deck.getCard(i);
			cardName = 	theCard.toString();
			
			sb.append(cardName);
			sb.append(" ");
		
		}
		System.out.println(sb.toString());
	}
	

	
	private void dealCard(){
		if (topOfDeck > (deck.getSize() - 1)){
			System.out.println("No more cards in the Deck!");
		}
		else {
			Card newCard;
			newCard = deck.getCard(topOfDeck);
			playingField.add(newCard.getImage());
			
			topOfDeck++;
			pilesOnField++;
			frame.cardDisplay(playingField);
						
			if (topOfDeck > (deck.getSize() - 1)){
				frame.allDone();
			}
			
		}
	}
	
	private int lastPileBackOne(){
		int changeMade = 0;
		if (pilesOnField < 1){
			System.out.println("Not enough piles available to do that!");
		}
		else{
			String sourceCard;
			String destinationCard;
			Character cardVal;
			Character cardSuit;
			
			sourceCard = playingField.get(pilesOnField);
			destinationCard = playingField.get(pilesOnField - 1);
			
			cardVal = sourceCard.charAt(0);
			cardSuit = sourceCard.charAt(1);
			if (cardVal.equals(destinationCard.charAt(0)) || cardSuit.equals(destinationCard.charAt(1))){
			
				playingField.remove(pilesOnField - 1);

				
				pilesOnField--;
				
				frame.cardDisplay(playingField);
				changeMade = 1;
			}
			else{
				System.out.println("Those cards don't match! Either value or suit must be the same.");
			}
		}
		return changeMade;
	}
	
	private int lastPileBackTwo(){
		int changeMade = 0;
		if (pilesOnField < 3){
			System.out.println("Not enough piles available to do that!");
		}
		else{
			String sourceCard;
			String destinationCard;
			Character cardVal;
			Character cardSuit;
			
			sourceCard = playingField.get(pilesOnField);
			destinationCard = playingField.get(pilesOnField - 3);
			
			cardVal = sourceCard.charAt(0);
			cardSuit = sourceCard.charAt(1);
			
			if (cardVal.equals(destinationCard.charAt(0)) || cardSuit.equals(destinationCard.charAt(1))){
				playingField.remove(pilesOnField - 3);
				playingField.add(pilesOnField - 3, sourceCard);
				playingField.remove(pilesOnField);

				
				pilesOnField--;
				
				frame.cardDisplay(playingField);
				changeMade = 1;
			}
			else{
				System.out.println("Those cards don't match! Either value or suit must be the same.");
			}
		}
		return changeMade;
	}
	
	private int amalgamatePiles(int firstIndex, int secondIndex) throws InputMismatchException{
		int changeMade = 0;
		if (secondIndex>pilesOnField || firstIndex<0 || secondIndex<firstIndex){
			changeMade = 0;
		}
		else{
			
			if (pilesOnField < 2){
				System.out.println("Not enough piles available to do that!");
			}
			else{
				if (secondIndex == (firstIndex + 1) || secondIndex == (firstIndex + 3)){
					String sourceCard;
					String destinationCard;
					Character cardVal;
					Character cardSuit;
					
					sourceCard = playingField.get(secondIndex);
					destinationCard = playingField.get(firstIndex);
					
					cardVal = sourceCard.charAt(0);
					cardSuit = sourceCard.charAt(1);
					
					if (cardVal.equals(destinationCard.charAt(0)) || cardSuit.equals(destinationCard.charAt(1))){
						playingField.remove(firstIndex);
						playingField.add(firstIndex, sourceCard);
						playingField.remove(secondIndex);
	
						
						pilesOnField--;
						
						frame.cardDisplay(playingField);
						changeMade = 1;
					}
					else{
						System.out.println("Those cards don't match! Either value or suit must be the same.");
					}
				}
				else{
					System.out.println("Those piles are not in the correct positions!");
				}
			}
		}
		return changeMade;
	}
	
	private void playForMe() throws InputMismatchException{
		int requiredPlays;
		int changeMade;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("How many moves would you like to be played?");
		requiredPlays = scan.nextInt();
		for(int i = 0; i < requiredPlays; i++){
			try {
				// 0.1 second sleep
			    Thread.sleep(100);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			changeMade = autoPlay();
			
			if (changeMade == 0){
				System.out.println("No more moves available!");
				break;
			}
		}
	}
	
	private int autoPlay(){
		int changeMade = 0;
		
		changeMade = lastPileBackOne();
		
		if (changeMade == 0){
			
			changeMade = lastPileBackTwo();
			
			if (changeMade == 0){
				
				for (int i = 1; i < pilesOnField; i++){
					changeMade = amalgamatePiles(i-1, i);
					if (changeMade == 1){
						break;
					}
					else{
						changeMade = amalgamatePiles(i-1, i+2);
						if (changeMade == 1){
							break;
						}
					}
				}
			}
		}
		if (changeMade == 0 && topOfDeck != (deck.getSize())){
			dealCard();
			changeMade = 1;
		}
		return changeMade;
	}
	
	
	private void restart(){
		frame.dispose();
		Game app = new Game();
		app.initialise();
		app.runMenu();
	}
	

}
