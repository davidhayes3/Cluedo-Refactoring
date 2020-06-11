/***************************************************************/
/* RoomButton Class
/* 
/* Represents the GUI button for a room slot
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;

@SuppressWarnings("serial")
public class RoomButton extends Button {

	Color defaultColor;	
	
	public RoomButton(int row, int col) {
		super(row, col);
		defaultColor = Color.BLACK;
		this.setBackground(this.defaultColor);
	}
	
	// changeColor() method
	public void changeColor(Color newColor) {
		this.setBackground(newColor);
	}
	
	// resetDefault() method
	public void resetDefaultColor() {
		this.setBackground(this.defaultColor);
	}
}