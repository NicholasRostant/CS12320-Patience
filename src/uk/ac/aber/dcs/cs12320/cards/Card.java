package uk.ac.aber.dcs.cs12320.cards;

/**
 * Holds the values for a standard playing card including value, suit and image
 * file
 * 
 * @author Nicholas Rostant
 * @version 1.0(3rd May 2015)
 *
 */
public class Card {
	private String value;
	private String suit;
	private String imgFile;

	/**
	 * Constructor takes the value and the suit of the card
	 * 
	 * @param cardVal
	 * @param cardSuit
	 */
	public Card(String cardVal, String cardSuit) {
		value = cardVal;
		suit = cardSuit;

	}

	/**
	 * The card value
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * The card suit
	 * 
	 * @return suit
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * The image file for the card
	 * 
	 * @return imgFile
	 */
	public String getImage() {
		StringBuilder fileName = new StringBuilder(10);
		fileName.append(value);
		fileName.append(suit);
		fileName.append(".gif");
		imgFile = fileName.toString();
		return imgFile;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(value);
		sb.append(suit);

		return sb.toString();
	}
}
