package uk.ac.aber.dcs.cs12320.cards;

public class Card {
	private String value;
	private String suit;
	private String imgFile;
	
	public Card (String cardVal, String cardSuit){
		value = cardVal;
		suit = cardSuit;
		
	}
	
	public String getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getImage() {
		StringBuilder fileName = new StringBuilder(10);
		fileName.append(value);
		fileName.append(suit);
		fileName.append(".gif");
		imgFile = fileName.toString();
		return imgFile;
	}

}
