/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run(){
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables. 

		// Creates Instruction Screen
		final JFrame inst = new JFrame("INSTRUCTIONS");
		inst.setLocation(300,75);

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("SPACE INVADERS");
		frame.setLocation(300,100);


		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		JLabel status = new JLabel();
		status_panel.add(status);

		// Score panel
		final JPanel score_panel = new JPanel();
		frame.add(score_panel, BorderLayout.WEST);
		JLabel score = new JLabel();
		status_panel.add(score);

		// Lives panel
		final JPanel lives_panel = new JPanel();
		frame.add(lives_panel, BorderLayout.LINE_START);
		JLabel lives = new JLabel();
		status_panel.add(lives);

		// Instruction Information
		final IntroCourt introthing = new IntroCourt();
		inst.add(introthing, BorderLayout.CENTER);

		// Main playing area
		final GameCourt court = new GameCourt(status, score, lives);
		frame.add(court, BorderLayout.CENTER);


		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is 
		// an instance of ActionListener with its actionPerformed() 
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("PLAY");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		control_panel.add(reset);


		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		introthing.introstart();

		// Put instruction frame on screen over game
		inst.setSize(775,625);
		inst.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		inst.setVisible(true);

		// Start game
		court.start();

	}

	/*
	 * Main method run to start and run the game
	 * Initializes the GUI elements specified in Game and runs it
	 * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Game());
	}
}
