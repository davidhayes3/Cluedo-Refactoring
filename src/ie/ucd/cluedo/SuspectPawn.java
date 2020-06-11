/***************************************************************/
/* SuspectPawn Class
/* 
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;

public class SuspectPawn extends Pawn {
	
	public Slot position;
	public BoardButton button;
	public Color color;
	
	public SuspectPawn(int pawnIndex, Color color) {
		super(pawnIndex);
		this.color = color;
	}
	
	// movePosition() method
	public void movePosition(Slot newPosition) {
		// Reset default of old button unless slot is pawn is currently not allocated to a slot
		if(this.position != null) {
			this.position.getButton().resetDefaultColor();
		}
		
		// Move to new slot and update color
		this.position = newPosition;
		this.position.getButton().changeColor(this.color);
	}
	
	// getPosition() method
	public Slot getPosition() { return this.position; }
	
	// getColor() method
	public Color getColor() { return this.color; }
}
