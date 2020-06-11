/***************************************************************/
/* Pawn Class
/* 
/* Represents a pawn on the board in the game
/***************************************************************/

package ie.ucd.cluedo;

import static ie.ucd.cluedo.Constants.PawnType;
import static ie.ucd.cluedo.Constants.Name;

public abstract class Pawn {

	int index;
	private PawnType type;
	private Name name;
	public Slot position;
	
	public Pawn(int index) {
		this.index = index;
		
		// Decide type of pawn based on index number of pawn
		if (index < Constants.NUM_SUSPECTS) {
			this.type = PawnType.SUSPECT;
			
		} else if (index < Constants.NUM_PAWNS) {
			this.type = PawnType.WEAPON;
		
		}
		
		this.name = Constants.GAME_LIST.get(index);
	}
	
	// getPawnIndex() method
	public int getPawnIndex() { return this.index; }
	
	// getType() method
	public PawnType getType() { return this.type; }
	
	// getName() method
	public Name getName() { return this.name; }
}