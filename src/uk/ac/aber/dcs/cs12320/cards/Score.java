package uk.ac.aber.dcs.cs12320.cards;

public class Score {
	private String name;
	private int score;
	
	Score(String n, int s){
		name = n;
		score = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score = s;
	}

	@Override
	public String toString() {
		return (name + " " + score);
	}
	
	
	
	

}
