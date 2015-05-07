package uk.ac.aber.dcs.cs12320.cards;

/**
 * Standard application class, creates an instance of the game and runs it
 * @author Nicholas Rostant
 * @version 1.1(3rd May 2015)
 *
 */
public class Application {
	
	
	public static void main(String args[]) {
		Game app = new Game();
		app.initialise();
		app.runMenu();
		
	}
	
}
