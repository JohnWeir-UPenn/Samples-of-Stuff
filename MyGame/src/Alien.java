/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Alien extends GameObj {
	public String img_file = "badguy.jpg";
	public boolean Destroyed = false;
	public static final int SIZE = 20;       
	public static final int INIT_POS_X = 20;  
	public static final int INIT_POS_Y = 30; 
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;

	public Alien(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error2:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g){
		g.drawImage(img, pos_x, pos_y, width, height, null); 
	}

}