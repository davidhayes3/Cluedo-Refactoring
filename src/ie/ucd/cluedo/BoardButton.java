/***************************************************************/
/* Board Button Class
/* 
/* Represents a GUI button for a slot in the corridor
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;

@SuppressWarnings("serial")
public class BoardButton extends Button {

	final Color defaultColor;	
	
	public BoardButton(int col, int row) {
		super(col, row);
		this.defaultColor = Color.YELLOW;
		this.setBackground(this.defaultColor);
	}
	
//	// changeColor() method
//	public void changeColor(Color newColor) {
//		this.setBackground(newColor);
//	}
	
	// resetDefault() method
	public void resetDefaultColor() {
		this.setBackground(this.defaultColor);
	}
}