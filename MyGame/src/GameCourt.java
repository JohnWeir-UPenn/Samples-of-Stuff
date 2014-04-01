/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Cannon cannon;          // the Black Cannon, keyboard control
	private Alien[] alien1;
	private Alien2[] alien2;
	private Alien3[] alien3;
	private Background black;
	private Bullet bullet;
	private Shot shot1;
	private Shot shot2;
	private boolean dead;
	private DeadCannon deadcannon;
	private int thescore;
	private int thelives;
	private int level;
	private Line line;

	public boolean playing = false;  // whether the game is running
	private JLabel status;
	private JLabel myscore;
	private JLabel mylives;

	// Game constants
	public static final int COURT_WIDTH = 750;
	public static final int COURT_HEIGHT = 450;
	public static final int SQUARE_VELOCITY = 8;
	// Update interval for timer in milliseconds 
	public static int INTERVAL = 35; 

	public GameCourt(JLabel status, JLabel score, JLabel lives){
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Initializes game state
		line = new Line(COURT_WIDTH, COURT_HEIGHT);
		alien1 = new Alien [10];
		alien2 = new Alien2 [10];
		alien3 = new Alien3 [10];
		dead = false;
		thelives = 3;

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called 
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (dead) {paused(); } 
				else tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the cannon to move as long
		// as an arrow key is pressed, by changing the cannon's
		// velocity accordingly. (The tick method below actually 
		// moves the cannon.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					cannon.v_x = -SQUARE_VELOCITY;
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					cannon.v_x = SQUARE_VELOCITY;
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
					if (bullet == null) {
						bullet = new Bullet (COURT_WIDTH, COURT_HEIGHT, 
								cannon.pos_x + 18, cannon.pos_y); }

			}
			public void keyReleased(KeyEvent e){
				cannon.v_x = 0;
				cannon.v_y = 0;
			}
		});
		this.mylives = lives;
		this.status = status;
		this.myscore = score;
	}

	/** (Re-)set the state of the game to its initial state.
	 */

	public void newlevel() {
		black = new  Background(COURT_WIDTH, COURT_HEIGHT); // reset background
		cannon = new Cannon(COURT_WIDTH, COURT_HEIGHT); // reset cannon
		deadcannon = null;
		bullet = null;
		shot1 = null;
		shot2 = null;
		// reset top row
		for (int i = 0; i < alien1.length; i++) {
			alien1[i] = new Alien(COURT_WIDTH, COURT_HEIGHT);
			alien1[i].pos_x = alien1[i].pos_x + 30*i;
			alien1[i].v_x = level + alien1[i].v_x;
		}
		// reset second row
		for (int i = 0; i < alien2.length; i++) {
			alien2[i] = new Alien2(COURT_WIDTH, COURT_HEIGHT);
			alien2[i].pos_x = alien2[i].pos_x + 30*i;
			alien2[i].v_x = level + alien2[i].v_x;
		}
		// reset third row
		for (int i = 0; i < alien3.length; i++) {
			alien3[i] = new Alien3(COURT_WIDTH, COURT_HEIGHT);
			alien3[i].pos_x = alien3[i].pos_x + 30*i;
			alien3[i].v_x = level + alien3[i].v_x;
		}
	}

	public void reset() {
		black = new  Background(COURT_WIDTH, COURT_HEIGHT); // reset background
		cannon = new Cannon(COURT_WIDTH, COURT_HEIGHT); // reset cannon
		deadcannon = null;
		bullet = null;
		shot1 = null;
		shot2 = null;
		thelives = 3;
		thescore = 0;
		level = 1;
		// reset top row
		for (int i = 0; i < alien1.length; i++) {
			alien1[i] = new Alien(COURT_WIDTH, COURT_HEIGHT);
			alien1[i].pos_x = alien1[i].pos_x + 30*i;
		}
		// reset second row
		for (int i = 0; i < alien2.length; i++) {
			alien2[i] = new Alien2(COURT_WIDTH, COURT_HEIGHT);
			alien2[i].pos_x = alien2[i].pos_x + 30*i;
		}
		for (int i = 0; i < alien2.length; i++) {
			alien2[i] = new Alien2(COURT_WIDTH, COURT_HEIGHT);
			alien2[i].pos_x = alien2[i].pos_x + 30*i;
		}
		// reset third row
		for (int i = 0; i < alien3.length; i++) {
			alien3[i] = new Alien3(COURT_WIDTH, COURT_HEIGHT);
			alien3[i].pos_x = alien3[i].pos_x + 30*i;
		}

		playing = true;

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	//start up condition
	public void start() {
		level = 1;
		thescore = 0;
		black = new Background (COURT_WIDTH, COURT_HEIGHT); // set background
		cannon = new Cannon(COURT_WIDTH, COURT_HEIGHT); // set cannon
		// set top row
		for (int i = 0; i < alien1.length; i++) {
			alien1[i] = new Alien(COURT_WIDTH, COURT_HEIGHT);
			alien1[i].pos_x = alien1[i].pos_x + 30*i;
		}
		// set second row
		for (int i = 0; i < alien2.length; i++) {
			alien2[i] = new Alien2(COURT_WIDTH, COURT_HEIGHT);
			alien2[i].pos_x = alien2[i].pos_x + 30*i;
		}
		// set third row
		for (int i = 0; i < alien3.length; i++) {
			alien3[i] = new Alien3(COURT_WIDTH, COURT_HEIGHT);
			alien3[i].pos_x = alien3[i].pos_x + 30*i;
		}


		playing = false;
		status.setText("Click PLAY to Begin");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined
	 * in the constructor triggers.
	 */

	// generates a random number between 0 and 9 to identify a shooting alien
	private int choose(){
		double temp = 10 * Math.random();
		int i = (int)temp;
		return i;
	}

	// shoots if the lower levels are completely destroyed
	private Alien shoottop(){
		Alien shooter = alien1[choose()];
		if (shooter == null) return shoottop();
		else if (shooter.Destroyed) return shoottop();
		else return shooter;
	}

	// shoots if the lowest level is completely destroyed
	private Alien2 shootmiddle() {
		Alien2 shooter = alien2[choose()];
		if (shooter == null) return shootmiddle();
		else if (shooter.Destroyed) return shootmiddle();
		else return shooter;
	}

	// first shoots at the cannon
	private Alien3 shootbottom() {
		Alien3 shooter = alien3[choose()];
		if (shooter == null) return shootbottom();
		else if (shooter.Destroyed) return shootbottom();
		else return shooter;
	}

	// condition that occurs when the cannon is hit
	void paused() {
		if (playing) {

			// replaces cannon with explosion
			deadcannon = new DeadCannon(COURT_WIDTH, COURT_HEIGHT);
			deadcannon.pos_x  = cannon.pos_x;
			deadcannon.pos_y = cannon.pos_y;

			// replaces cannon in center			
			cannon = new Cannon(COURT_WIDTH, COURT_HEIGHT);

			//resets the rest
			bullet = null;
			shot1 = null;
			shot2 = null;
			dead = false;
			repaint();
		}
	}

	// what to do at each time step
	void tick() {
		if (playing) {
			deadcannon = null;
			// sets general output message
			status.setText("Engaging alien wave " + Integer.toString(level)+ "   ");

			// controls the first shot fired by the aliens
			if (shot1 == null) {
				int count1 = 0;
				for (int i = 0; i < alien3.length; i++) {
					if (alien3[i]!=null) {if (!alien3[i].Destroyed) count1++ ;}
				}
				if (count1 > 1) shot1 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
						shootbottom().pos_x + 18, shootbottom().pos_y); 
				else {
					int count2 = 0;
					for (int i = 0; i < alien2.length; i++) {
						if (alien2[i]!=null) {if(!alien2[i].Destroyed)count2++;}
					}
					if (count2 > 1) shot1 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
							shootmiddle().pos_x + 18, shootmiddle().pos_y);
					else {
						int count3 = 0;
						for (int i = 0; i < alien1.length; i++) {
							if (alien1[i]!=null) {if(!alien1[i].Destroyed)count3++;}
						}
						if (count3 != 0) shot1 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
								shoottop().pos_x + 18, shoottop().pos_y);

					}
				}
			}

			// controls the second shot fired by the aliens
			if (shot2 == null) {
				int count4 = 0;
				for (int i = 0; i < alien3.length; i++) {
					if (alien3[i]!=null) {if (!alien3[i].Destroyed) count4++ ;}
				}
				if (count4 != 0) {shot2 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
						shootbottom().pos_x + 18, shootbottom().pos_y); 
				StdAudio.play("zap.wav"); }
				else {
					int count5 = 0;
					for (int i = 0; i < alien2.length; i++) {
						if (alien2[i]!=null) {if(!alien2[i].Destroyed)count5++;}
					}
					if (count5 != 0) { shot2 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
							shootmiddle().pos_x + 18, shootmiddle().pos_y);
					StdAudio.play("zap.wav"); }
					else {
						int count6 = 0;
						for (int i = 0; i < alien1.length; i++) {
							if (alien1[i]!=null) {if(!alien1[i].Destroyed)count6++;}
						}
						if (count6 != 0) {shot2 = new Shot(COURT_WIDTH, COURT_HEIGHT, 
								shoottop().pos_x + 18, shoottop().pos_y);
						StdAudio.play("zap.wav");}
						else {level = level + 1; 
						newlevel();
						thescore=thescore + 100;
						StdAudio.play("up.wav");}
					}
				}
			}

			// move bullet and delete if it reaches the roof
			if (bullet!= null) {
				bullet.move();
				if (bullet.pos_y == 0) bullet = null;
			}

			// controls movement of first shot
			if (shot1 != null) {
				shot1.move();
				if (shot1.pos_y > 435) shot1 = null;
			}
			// controls movement of second shot
			if (shot2 != null) {
				shot2.move();
				if (shot2.pos_y > 435) shot2 = null;
			}

			// For Top Row

			// change alien direction if a wall is reached
			if (alien1[0] != null && alien1[9] != null){
				if (alien1[0].pos_x == 0 || alien1[9].pos_x == 730) {
					for (int i = 0; i < alien1.length; i++) {
						if (alien1[i]!=null){alien1[i].v_x = -alien1[i].v_x;
						alien1[i].pos_y=alien1[i].pos_y+15;
						if (alien1[i].pos_y >= 385 && !alien1[i].Destroyed){
							playing = false;
							status.setText("You have been overrun and Earth "+
									"has been destroyed. You are a terrible "+
									"defender of humanity.    ");	 
						}   
						}
					}
				}
			}


			// move the aliens as group
			for (int i = 0; i < alien1.length; i++) {
				if (alien1[i] != null) 
					alien1[i].move();
			}

			// destroy both bullet and aliens if bullet hits
			for (int i = 0; i < alien1.length; i++) {
				if (alien1[i] != null && bullet != null) {
					if (alien1[i].intersects(bullet) && !alien1[i].Destroyed) { 
						alien1[i].Destroyed=true;
						thescore += 20;
						StdAudio.play("bomb-02.wav");
						bullet = null;
						if (i == 0 || i == 10 ) {
							alien1[i].img_file = "black.jpg";
						}
					}
				}
			}

			// For Second Row

			// change alien direction if a wall is reached
			if (alien2[0] != null && alien2[9] != null){
				if (alien2[0].pos_x == 0 || alien2[9].pos_x == 730) {
					for (int i = 0; i < alien2.length; i++) {
						if (alien2[i]!=null){alien2[i].v_x = -alien2[i].v_x;
						alien2[i].pos_y=alien2[i].pos_y+15;
						if (alien2[i].pos_y >= 385 && !alien2[i].Destroyed){
							playing = false; 
							status.setText("You have been ovverun and Earth "+
									"has been destroyed. You are a terrible "+
									"defender of humanity.    ");
						}
						}
					}
				}
			}

			// move the aliens as group
			for (int i = 0; i < alien2.length; i++) {
				if (alien2[i] != null) 
					alien2[i].move();
			}

			// destroy both bullet and aliens if bullet hits
			for (int i = 0; i < alien2.length; i++) {
				if (alien2[i] != null && bullet != null) {
					if (alien2[i].intersects(bullet) && !alien2[i].Destroyed) { 
						alien2[i].Destroyed=true;
						thescore +=15;
						StdAudio.play("bomb-02.wav");
						bullet = null;
						if (i == 0 || i == 10 ) {
							alien2[i].img_file = "black.jpg";
						}
					}
				}
			}

			//For third row

			//change alien direction if a wall is reached
			if (alien3[0] != null && alien3[9] != null){
				if (alien3[0].pos_x == 0 || alien3[9].pos_x == 730) {
					for (int i = 0; i < alien3.length; i++) {
						if (alien3[i]!=null){alien3[i].v_x = -alien3[i].v_x;
						alien3[i].pos_y=alien3[i].pos_y+15;
						if (alien3[i].pos_y >= 385 && !alien3[i].Destroyed){
							playing = false;
							status.setText("You have been ovverrun and Earth "+
									"has been destroyed. You are a terrible "+
									"defender of humanity.    ");
						}   
						}
					}
				}
			}

			// move the aliens as group
			for (int i = 0; i < alien3.length; i++) {
				if (alien3[i] != null) 
					alien3[i].move();
			}

			// destroy both bullet and aliens if bullet hits
			for (int i = 0; i < alien3.length; i++) {
				if (alien3[i] != null && bullet != null) {
					if (alien3[i].intersects(bullet) && !alien3[i].Destroyed) { 
						alien3[i].Destroyed=true;
						thescore += 10;
						StdAudio.play("bomb-02.wav");
						bullet = null;
						if (i == 0 || i == 10) {
							alien3[i].img_file = "black.jpg";
						}
					}
				}
			}

			// destroy cannon if shot
			if (shot1 != null) {
				if (shot1.intersects(cannon)){ dead = true; 
				thelives = thelives - 1;
				StdAudio.play("bigboom.wav");
				cannon.img_file = "dead.jpg";
				}
			}
			if (shot2 != null) {
				if (shot2.intersects(cannon)){ dead = true; 
				thelives = thelives - 1;
				StdAudio.play("bigboom.wav");}
				cannon.img_file = "dead.jpg";
			}

			// ends the game when all lives are lost
			if (thelives <= 0) {
				playing = false; 
				StdAudio.play("bomb-02.wav");
				status.setText("You have been killed and Earth has been " +
						"destroyed. You are a terrible defender " +
						"of humanity.    ");
			}

			//makes sure lives cannot become negative
			if (thelives <= 0) {
				thelives = 0;
				deadcannon = new DeadCannon(COURT_WIDTH, COURT_HEIGHT);
				deadcannon.pos_x = cannon.pos_x;
				StdAudio.play("loss.wav");
			}

			//set the text labels at the bottom of the screen
			myscore.setText("Score = "+ Integer.toString(thescore)+ "   ");
			mylives.setText("Lives = "+ Integer.toString(thelives));
			repaint();
		
			//move cannon
			cannon.move();
		} 
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (deadcannon != null) {deadcannon.draw(g); System.out.println(deadcannon.pos_x); System.out.println(cannon.pos_x); }
		black.draw(g);
		line.draw(g);
		cannon.draw(g);
		if (bullet != null) bullet.draw(g);
		if (shot1 != null) shot1.draw(g);
		if (shot2 != null) shot2.draw(g);
		for (int i = 0; i < alien1.length; i++) {
			if (!alien1[i].Destroyed && alien1[i] != null) alien1[i].draw(g);
		}
		for (int i = 0; i < alien2.length; i++) {
			if (!alien2[i].Destroyed && alien2[i] != null) alien2[i].draw(g);
		}
		for (int i = 0; i < alien3.length; i++) {
			if (!alien3[i].Destroyed && alien3[i] != null) alien3[i].draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
