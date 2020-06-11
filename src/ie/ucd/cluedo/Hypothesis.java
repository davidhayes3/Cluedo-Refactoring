/***************************************************************/ 
/* Hypothesis Class
/* 
/***************************************************************/

package ie.ucd.cluedo;

public class Hypothesis {
	
	private int suspect;
	private int weapon;
	private int room;
	
	public Hypothesis(int suspect, int weapon, int room) {
		this.suspect = suspect;
		this.weapon = weapon;
		this.room = room;	
	}
	
	// getSuspect() method
	public int getSuspect() { return this.suspect; }
	
	// getWeapon() method
	public int getWeapon() { return this.weapon; }

	// getRoom() method
	public int getRoom() { return this.room; }
	
	// validateHypothesis()
//	Boolean validateHypothesis(Hypothesis h) {
//		isHypothesisTrue = false;
//		
//		if (h == this.hypothesis) {
//			isHypothesisTrue = true;
//		}
//		
//		return isHypothesisTrue;
//	}
}