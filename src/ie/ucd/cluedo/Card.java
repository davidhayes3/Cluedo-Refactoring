/***************************************************************/
/* Card Class
/* 
/* Represent a card held by a player
/***************************************************************/

package ie.ucd.cluedo;

import static ie.ucd.cluedo.Constants.CardType;
import static ie.ucd.cluedo.Constants.Name;

public class Card {	
	
	private CardType cardType;
	private Name cardName;
	private int cardIndex;
	
	public Card(int cardIndex) {
		
		this.cardIndex = cardIndex;
		
		// Decide type of card based on index number of card
		if (cardIndex < Constants.NUM_SUSPECTS) {
			this.cardType = CardType.SUSPECT;
			
		} else if (cardIndex < Constants.NUM_PAWNS) {
			this.cardType = CardType.WEAPON;
			
		}
		else if (cardIndex < Constants.NUM_CARDS_IN_DECK) {
			this.cardType = CardType.ROOM;
		}

		this.cardName = Constants.GAME_LIST.get(cardIndex);
	}
	
	// getType() method
	public CardType getType() { return this.cardType; }
	
	// getName() method
	public Name getName() { return this.cardName; }
	
	// getCardIndex()
	public int getCardIndex() { return this.cardIndex; }
}