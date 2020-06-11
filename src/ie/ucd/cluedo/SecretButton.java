/***************************************************************/
/* SecretButton Class
/* 
/* Represents the GUI button for a secret passage slot
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//@SuppressWarnings("serial")
public class SecretButton extends Button {	

	ImageIcon image;
	
	public SecretButton(int row, int col) {
		super(row, col);
		setImage();
	}
	
	private void setImage() {
		// Set image
		try {
			image = new ImageIcon(ImageIO.read(new File("img/Stairs.jpg")));
		
		} catch (IOException e) {
			System.out.println("Error finding stairs image.");
		}
			
		this.setIcon(this.image);
	}
	
	// changeColor() method
	public void changeColor(Color newColor) {
		this.setBackground(newColor);
	}
	
	// resetDefault() method
	public void resetDefaultColor() {
		this.setIcon(this.image);
	}
}