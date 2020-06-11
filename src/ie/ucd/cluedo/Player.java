/***************************************************************/
/* Player Class
/* 
/* Represents a player of the game game
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;

public class Player {

	private int playerNumber;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private SuspectPawn suspectPawn;
	private Notebook notebook;
	public CardView cardView;	
	
	public Player(int playerNumber, Notebook playerNotebook) {
		this.playerNumber = playerNumber;
		this.notebook = playerNotebook;
	}
	
	// getPlayerNumber() method
	public int getPlayerNumber() { return this.playerNumber; }
	
	// getCards() method
	public ArrayList<Card> getCards() { return this.cards; }
	
	// getSuspectPawn() method
	public SuspectPawn getSuspectPawn() { return this.suspectPawn; }
	
	// setSuspectPawn() method
	public void setSuspectPawn(SuspectPawn suspectPawn) { this.suspectPawn = suspectPawn; }

	// getNotebook() method
	public Notebook getNotebook() { return this.notebook; }
	
	// getCardView() method
	public CardView getCardView() { return this.cardView; }
	
	// giveHand() method
	public void setCardView(CardView cardView) { this.cardView = cardView; }
	
	// giveCard() method, allocates a single card to a player
	public void addCard(Card card) {
		this.cards.add(card);
	}
}
