/***************************************************************/
/* Button Class
/* 
/* Abstract Class representing a GUI button
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class Button extends JButton {
	
	public Button(int row, int col) {
		this.setBounds( Constants.BUTTON_PIXEL_WIDTH * col, Constants.BUTTON_PIXEL_HEIGHT * row, Constants.BUTTON_PIXEL_WIDTH, Constants.BUTTON_PIXEL_HEIGHT );			
	}

	// changeColor() method
	public void changeColor(Color newColor) {
		this.setBackground(newColor);
	}
	
	// Abstract method to reset default settings of button
	public abstract void resetDefaultColor();
}