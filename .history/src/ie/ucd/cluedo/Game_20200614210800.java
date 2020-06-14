/***************************************************************/
/* Game Class
/* 
/* Implements all main actions in game of game
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	ArrayList<Player> players;
	Board board;
	ArrayList<SuspectPawn> suspectPawns;// 6 Suspect Pawns
	// 6 Weapon
	// 2 Dies
	Envelope envelope;
	ArrayList<Card> cards;
	int playerTurn;
	boolean isGameOver;
	
	public Game(int numPlayers) {
		
		System.out.println("NEW GAME\n");
		
		// createEquipment()
		this.board = new Board();
		//this.suspectPawns = createSuspectPawns(); 
		// new ArrayList<SuspectPawn>() + putOnStartingSquare
		// new ArrayList<WeaponPawn>()
		// new Die()
		this.cards = createCards();
		this.envelope = new Envelope();
		
		this.players = createPlayers(numPlayers);

		// sortCards
		// fillEnvelope
		
		// allocateRemainingCards
		// writeCardsInNotebook
		
		allocatePawns(this.players, this.board.getSuspectPawns());
		//allocatePawns(this.players, this.suspectPawns);
		
		// removeMurderCardsFromDeck
		allocateCards(this.players, this.cards);
		
		this.playerTurn = 0;
	    this.isGameOver = false;
	}
	
	// makeSuspectPawns() Method
	// Purpose: Creates suspect pawns for each game character and places them at set locations on board
	private ArrayList<SuspectPawn> createSuspectPawns() {		
		
		ArrayList<SuspectPawn> suspectPawns = new ArrayList<SuspectPawn>(Constants.NUM_SUSPECTS);
		
		// Initial locations for each suspect pawn is the same for every game
		int[] x0 = {9, 14, 23, 23, 15, 0};
		int[] y0 = {0, 0, 5, 12, 23, 16};
				
		for (int pawnIndex = 0; pawnIndex < Constants.NUM_SUSPECTS; pawnIndex++) {
			// Create suspect pawns and position on board
			suspectPawns.add(new SuspectPawn(pawnIndex, Constants.PAWN_COLORS[pawnIndex]));
			suspectPawns.get(pawnIndex).movePosition(this.board.getBoardSlots()[y0[pawnIndex]][x0[pawnIndex]]);
		}
		
		return suspectPawns;
	}

	// makePlayers() Method
	// Purpose: Creates ArrayList containing all the players of the current game
	public ArrayList<Player> createPlayers(int numPlayers) {				
		
		players = new ArrayList<Player>(numPlayers);
		
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(i+1, new Notebook()));
		}
		
		return players;
	}
	
	// getCharacters() Method
	// Purpose: Asks each player one by one which character they want, and assigns the suspectPawn of that character to the player
	private void allocatePawns(ArrayList<Player> players, ArrayList<SuspectPawn> suspectPawns) {	
		
		int playerChoice;
		boolean isPlayerChoiceValid;
		
		for (int playerNumber = 1; playerNumber <= players.size(); playerNumber++) {	
			
			Utility.printCharactersAvailableToPlayer(playerNumber);
			
			while (true) {
				
				playerChoice = Utility.getCharacterChoiceFromPlayer();
				
				isPlayerChoiceValid = playerChoice >= Constants.MIN_PLAYER_NUMBER && playerChoice <= Constants.MAX_PLAYER_NUMBER;
				
				if (isPlayerChoiceValid) {
					allocateCharacterToPlayer(playerChoice, playerNumber, suspectPawns);
					Utility.printSuccessMessage(players, playerNumber);
					break;
				} else {
					Utility.printFailureMessage();
				}
			}
		}
	}
	
	private void allocateCharacterToPlayer(int playerChoice, int playerNumber, ArrayList<SuspectPawn> suspectPawns) {
		
		for (int pawnIndex = 0; pawnIndex < suspectPawns.size(); pawnIndex++) {
				
			if (suspectPawns.get(pawnIndex).getName() == Constants.GAME_LIST.get(playerChoice-1)) {
				// Give suspect pawn to player and remove from Boards list of suspect pawns
				players.get(playerNumber-1)
						.setSuspectPawn(suspectPawns.get(pawnIndex));
				suspectPawns.remove(pawnIndex);
			}
		}
	}
	
	// createDeck() Method
	// Purpose: Creates the card deck to be distributed among players i.e all cards minus murder cards
	private ArrayList<Card> createCards() {
		
		ArrayList<Card> cards = new ArrayList<Card>(Constants.NUM_CARDS_IN_PLAY);
		
		for (int cardIndex = 0; cardIndex < Constants.NUM_CARDS_IN_DECK; cardIndex++) {
			if (cardIndex == Constants.murderSuspectIndex || cardIndex == Constants.murderWeaponIndex || cardIndex == Constants.murderRoomIndex) {
				continue;
			} else {
				cards.add(new Card(cardIndex));
			}	
		}
		
		return cards;		
	}
	
	// allocateCards() Method
	// Purpose: Allocates the deck of cards created among all players, one at a time to each player and allocates a hand object to each player
	private void allocateCards(ArrayList<Player> players, ArrayList<Card> cards) {
		
		// randomlyAllocateSuspectCards
		// randomlyAllocateWeaponCards
		// randomlyAllocateRoomCards
		
		// for each player in players
		// randomly allocate suspect
		
		
		int playerNumber = 0;
		
		// Allocate cards one by one to each player
		for (int cardIndex = 0; cardIndex < Constants.NUM_CARDS_IN_PLAY; cardIndex++) {
		
			players.get(playerNumber).addCard(cards.get(cardIndex));
			playerNumber++;
			
			if (playerNumber == players.size()) {
				playerNumber = 0;
			}
		}
		
		// Create Card for each players
		for(int j = 0; j < players.size(); j++) {
			players.get(j).setCardView(new CardView(players.get(j).getCards()));
			players.get(j).getNotebook().addCards(players.get(j).getCards());
		}		
		
		// change order of this method - bit hard to understand -> get cards -> make hand -> assign to player
	}
	
	
	public void simulatePlayerTurns() {
		simulatePlayerTurns(this.players, this.board);
	}
	
	// gameTurns() Method
	// Purpose: Simulates the turns of the game. The options available to a player vary depending 
	// on where they are on board and if they have already rolled
	@SuppressWarnings("resource")
	private void simulatePlayerTurns(ArrayList<Player> players, Board board) {
	    
		// should be on player object
		boolean hasRolled = false;
		// should be on player object
		int currentRoom;

		System.out.println("\n\nGAME HAS STARTED");

		// Only break from loop if true accusation is made or only one player left
		while (!isGameOver) {// why not this. & not arguments to function		
			
			Turn turn = new Turn(this.playerTurn, this.isGameOver); // why not arguments to function
			
			currentRoom = players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(); // would current room TYPE not be better?
			
			// Print details of current turn
			System.out.println("\n" + players.get(playerTurn)
											.getSuspectPawn()
											.getName() + "'s turn (" 
					+ Constants.COLOR_MAP
							.get(players.get(playerTurn)
							.getSuspectPawn()
							.getColor()) + ")");
			
			// Vary choices available to player depending on their current circumstances in game
			if (hasRolled) {
				
				// If not in room and have already rolled this turn
				if (currentRoom == 0) {	
					
					// Next 3 lines can be a separate function
					Scanner scanner = new Scanner(System.in);
					System.out.printf("\nWhat do you want to do?\nView Cards [c]\nView Notebook [n]\nFinish Move [f]\nOption: "); // Strings in different class
					String playerChoice = scanner.nextLine();
					
					hasRolled = turn.afterRollMove(players, board, hasRolled, playerChoice);
					
				} else { // If in room and have already moved this turn
					
					Scanner scanner = new Scanner(System.in);
					System.out.printf("\nWhat do you want to do?\nMake Hypothesis [h]\nMake Accusation [a]\nView Cards [c]\nView Notebook [n]\nFinish Move [f]\nOption: ");
					String playerChoice = scanner.nextLine();
					hasRolled = turn.afterRollMoveInRoom(players, board, hasRolled, playerChoice);	
				
				}
			
			} else {
				// If not in room and have not already moved this turn
				if (currentRoom == 0) {			
					
					Scanner scanner = new Scanner(System.in);
					System.out.printf("\nWhat do you want to do?\nRoll Dice [r]\nView Cards [c]\nView Notebook [n]\nFinish Move [f]\nOption: " );
					String playerChoice = scanner.nextLine();
					hasRolled = turn.beforeRollMove(players, board, hasRolled, playerChoice);	
				
				} else { // If in room and have not already moved this turn
					
					Scanner scanner = new Scanner(System.in);
					System.out.printf("\nWhat do you want to do?\nRoll Dice [r]\nMake Hypothesis [h]\nMake Accusation [a]\nView Cards [c]\nView Notebook [n]\nFinish Move [f]\nOption: ");
					String playerChoice = scanner.nextLine();
					hasRolled = turn.beforeRollMoveInRoom(players, board, hasRolled, playerChoice);				
				
				}
			}
			
			this.playerTurn = turn.getPlayerTurn(); // should be attribute of player or game
			this.isGameOver = turn.getIsGameOver(); // setter for attribute of this method should not be contained in other method
		}
		
		System.out.println("\n\nGAME OVER");
	}
}