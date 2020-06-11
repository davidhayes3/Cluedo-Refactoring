/***************************************************************/
/* DoorButton Class
/* 
/* Represents a GUI button for the slot for the door of a room
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class DoorButton extends Button {	
	
	ImageIcon image;
	
	public DoorButton(int row, int col) {
		super(row, col);	
		setImage();
	}
	
	private void setImage() {
		// Set image of button
		try {
			this.image = new ImageIcon(ImageIO.read(new File("img/Door.jpg")));
		
		} catch (IOException e) {
			System.out.println("Error finding door image");
		}
			
		this.setIcon(this.image);
	}
	
	// changeColor() method
	public void changeColor(Color newColor) {
		this.setIcon(null);
		this.setBackground(newColor);
	}
	
	// resetDefault() method
	public void resetDefaultColor() {
		this.setIcon(this.image);
	}
}