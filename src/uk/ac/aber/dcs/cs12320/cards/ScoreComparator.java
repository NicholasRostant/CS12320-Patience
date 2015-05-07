package uk.ac.aber.dcs.cs12320.cards;

import java.util.Comparator;

/**
 * A comparator that allows us to order the scores by their values
 * @author Nicholas Rostant
 * @version 1.0(May 5th 2015)
 *
 */
public class ScoreComparator implements Comparator<Score> {
	
	@Override
	public int compare(Score s1, Score s2){
		if (s1.getScore() > s2.getScore()){
			return 1;
		} else if (s1.getScore() < s2.getScore()){
			return -1;
		}
		
		return 0;
	}

}
