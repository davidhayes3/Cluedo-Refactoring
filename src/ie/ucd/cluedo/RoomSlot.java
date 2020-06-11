/***************************************************************/
/* RoomSlot Class
/* 
/* Represents a room slot
/***************************************************************/

package ie.ucd.cluedo;

public class RoomSlot extends Slot {
	
	int roomNumber;
	RoomButton roomButton;
	
	public RoomSlot(int row, int col, int roomNumber, RoomButton roomButton) {
		super(row, col);
		this.roomNumber = roomNumber;
		this.roomButton = roomButton;
	}
	
	// getRoomNumber() method
	public int getRoomNumber() {
		return this.roomNumber;
	}
		
	// getButton() method
	public RoomButton getButton() {
		return this.roomButton;
	}
}