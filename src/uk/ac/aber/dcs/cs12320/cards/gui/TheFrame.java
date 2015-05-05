package uk.ac.aber.dcs.cs12320.cards.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Represents a window on which to draw the cards
 * @author Lynda Thomas (and Chris Loftus)
 * @version 1.1 (5th March 2015)
 *
 */
public class TheFrame extends JFrame {

	private ThePanel canvas;

	/**
	 * The constructor creates a Frame ready to display the cards
	 */
	public TheFrame() {

		// Calls the constructor in the JFrame superclass passing up the name to 
		// display in the title
		super("Becky's Patience");
		
		// When you click on the close window button the window will be closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// This has North, East, South, West and Center positions for components
		setLayout(new BorderLayout());

		// This is what we will draw on (see the inner class below)
		canvas = new ThePanel();
		setSize(700, 300);
		this.add(canvas, BorderLayout.CENTER);

		setVisible(true); // Display the window
	}

	/**
	 * Displays all cards
	 * 
	 * @param cards
	 *            an arraylist of strings of the form 3h.gif for 3 of hearts
	 */
	public void cardDisplay(ArrayList<String> cards) {
		canvas.cardDisplay(cards);
	}

	/**
	 * Call before cardDisplay at end of game (takes away the unused pile)
	 */
	public void allDone() {
		canvas.allDone();
	}

	// /////////////////////////////////////////////////

	/*
	 * This is an example of an inner class (like Russian dolls)
	 * It private so can only be seen by the outer class. It's part
	 * of the implementation of TheFrame. Because it extends JPanel we
	 * can draw on it
	 */
	private class ThePanel extends JPanel {
		ArrayList<String> cards = new ArrayList<String>();
		private Image image;
		private boolean done;
		private Color color = new Color(97,230,151);

		private ThePanel() {
			setBackground(color);
			done = false;
		}

		private void cardDisplay(ArrayList<String> c) {
			cards = c;
			repaint();
		}

		private void allDone() {
			done = true;
		}

		/**
		 * This is called automatically by Java when it want to draw this panel.
		 * So we have to put our drawing command in here. 
		 * @param g Is the graphics object on which we draw.
		 */
		@Override
		public void paintComponent(Graphics g) {
			// Always do this. It's giving the JPanel superclass a change to
			// paint its parts before we paint ours. E.g. we don't draw the
			// edge of the window, one of the super-classes does that.
			super.paintComponent(g);
			int x = 20;
			int y = 50;
			// Loop through all the cards get each image in turn
			for (String c : cards) {
				String file = "cards/" + c;
				image = Toolkit.getDefaultToolkit().getImage(file);
				g.drawImage(image, x, y, 70, 100, this);
				x += 72;  // The x position is moved on in order to position the next card
				          // This could be improved by having a horizontal scroll bar
			}
			if (!done) {
				// Draws the face-down top card of our pack of cards
				String file = "cards/b.gif";
				image = Toolkit.getDefaultToolkit().getImage(file);
				g.drawImage(image, 100, 152, 70, 100, this);
			}
		}
	} // ThePanel inner class

} 