package uk.ac.aber.dcs.cs12320.cards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
	
	ArrayList<Card> deck = new ArrayList<Card>();
	int deckSize;
	
	Deck(String fileName){
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			Scanner cardFile = new Scanner(br);
			
			deckSize = cardFile.nextInt();

			String cardVal;
			String cardSuit;
			for (int i = 0; i < deckSize; i++){
				cardVal = cardFile.next();
				cardSuit = cardFile.next();
				Card inCard = new Card(cardVal, cardSuit);
				
				deck.add(inCard);	
			}
			
			cardFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unexpected Error, file is missing or corrupt");
		}
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card getCard(int index){
		Card tempCard = deck.get(index);
		return tempCard;
	}
	
	public int getSize(){
		return deckSize;
	}

	@Override
	public String toString() {
		Card tempCard;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < deckSize; i++){
			tempCard = deck.get(i);
			sb.append(tempCard.toString());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	

}
