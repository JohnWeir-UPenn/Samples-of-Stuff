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

/** A basic game object displayed as a black square, starting in the 
 * upper left corner of the game court.
 *
 */
/** A game object displayed using an image. 
 * 
 * Note that the image is read from the file when the object is 
 * constructed, and that all objects created by this constructor share
 * the same image data (i.e. img is static). This important for 
 * efficiency, your program will go very slowing if you try to create 
 * a new BufferedImage every time the draw method is invoked. */
public class Bullet extends GameObj {
	public static final String img_file = "bullet.jpg";
	public static final int SIZE = 4;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = -13;

	private static BufferedImage img;

	public Bullet(int courtWidth, int courtHeight, int INIT_X, int INIT_Y) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
				SIZE, 5*SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error5:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g){
		g.drawImage(img, pos_x, pos_y, width, height, null); 
	}

}