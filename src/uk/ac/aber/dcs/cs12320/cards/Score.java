package uk.ac.aber.dcs.cs12320.cards;

/**
 * Class that allows us to store a score with a name attached
 * @author Nicholas Rostant
 * @version 1.0 (3rd May 2015)
 *
 */
public class Score {
	private String name;
	private int score;
	
	/**
	 * Simple constructor takes name and score
	 * @param n
	 * @param s
	 */
	Score(String n, int s){
		name = n;
		score = s;
	}

	/**
	 * Retrieve the name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the score
	 * @return score
	 */
	public int getScore() {
		return score;
	}


	@Override
	public String toString() {
		return (name + " " + score);
	}
}
