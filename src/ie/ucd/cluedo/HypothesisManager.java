/***************************************************************/
/* Hypothesis Manager Class
/* 
/* Implements the formulation and resulting actions arising from 
/* hypotheses
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;
import java.util.Scanner;

public class HypothesisManager {

	Hypothesis hypothesis;
	
	public HypothesisManager() {}
	
	// simulateHypothesis() Method
	// Purpose: Implements a full hypothesis and makes the necessary board repositions
	@SuppressWarnings("resource")
	public void simulateHypothesis(ArrayList<Player> players, Board board, int playerTurn) {

		Scanner scanner = new Scanner(System.in);
		
		// Get suspect in hypothesis
		System.out.println("\nWhat suspect is in your hypothesis:");
		for (int i = 0; i < Constants.NUM_SUSPECTS; i++) {
			System.out.println(i + ")" + Constants.GAME_LIST.get(i));
		}
		
		int suspect = scanner.nextInt();
		
		// Get weapon in hypothesis
		System.out.println("\nWhat weapon is in your hypothesis:");
		for (int i = 0; i < Constants.NUM_WEAPONS; i++) {
			System.out.println(i + ")" + Constants.GAME_LIST.get(i + Constants.NUM_SUSPECTS));
		}
		
		int weapon = scanner.nextInt() + Constants.NUM_SUSPECTS;
		
		// Room in hypothesis is the players current room
		int currentRoom = players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber();
		int room = currentRoom + Constants.NUM_SUSPECTS + Constants.NUM_WEAPONS - 1;
		
		// Create hypothesis object
		Hypothesis playerHypothesis = new Hypothesis(suspect, weapon, room);
		
		// Check cards of all players to see if any player refutes the hypothesis
		checkPlayersCards(players, playerHypothesis, playerTurn);
		
		// Move the suspect pawn of the hypothesis to a new room
		moveSuspectPawn(players, board, suspect, room);
	}
	
	// moveSuspectPawn() Method
	// Purpose: Moves the suspect pawn of the of the suspect in the hypothesis to the room in the hypothesis 
	private void moveSuspectPawn(ArrayList <Player> players, Board board, int suspect, int room) {
		
		// Checks if any of the players already have the suspect of the accusation, and if it's already in that room
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getSuspectPawn().getPawnIndex() == suspect) {
				if (players.get(i).getSuspectPawn().getPosition().getRoomNumber() == room - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
					return;
				}
			}
		}
		
		// Checks if any of the suspect pawns that are unallocated to a player have the suspect of the accusation, and if it's in the accusation room
		for (int j = 0; j < board.getSuspectPawns().size(); j++) {
			if (board.getSuspectPawns().get(j).getPawnIndex() == suspect) {
				if (board.getSuspectPawns().get(j).getPosition().getRoomNumber() == room - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
					return;
				}
			}
		}
		
		// Finds a free slot in the accusation room
		Slot roomSlot = getRoomSlot(players, board, room);
		
		// If any of the players have the suspect, move it to roomSlot
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).getSuspectPawn().getPawnIndex() == suspect) {	
				players.get(i).getSuspectPawn().movePosition(roomSlot);
				return;
			}
		}
		
		// Otherwise, the suspect pawn, is not allocated to a player, so find it in the Board's pawn list and move it to roomSlot
		for (int j = 0; j < board.getSuspectPawns().size(); j++) {
			
			if (board.getSuspectPawns().get(j).getPawnIndex() == suspect) {
				board.getSuspectPawns().get(j).movePosition(roomSlot);
				return;
			}
		}		
	}

	// getRoomSlot() Method
	// Purpose: Finds a free slot in the room of the accusation 
	private Slot getRoomSlot(ArrayList<Player> players, Board board, int room) {
	
		boolean slotOccupied;
		
		ArrayList<RoomSlot> roomSlots = board.getRoomSlots();
		
		// Find all roomSlots that are in the room of the accusation
		for (RoomSlot rs: roomSlots) {
			slotOccupied = false;
			
			if (rs.getRoomNumber() == room - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
				
				// Check if any of the players have suspect pawns in that room, if so, discard these slots
				for (Player p: players) {
					if (p.getSuspectPawn().getPosition() == board.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}
				
				// Check if any of the unallocated suspect pawns are in that room, if so, discard these slots
				for (int i = 0; i < board.getSuspectPawns().size(); i++) {
					if (board.getSuspectPawns().get(i).getPosition() == board.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}
				
				// If a slot passes the two above checks, return it as a viable slot
				if (!slotOccupied) {
					return board.getSlots()[rs.getRow()][rs.getCol()];
				}
			}
		}
		
		return null;
	}
	
	// checkPlayersCards() method
	// Purpose: Check the cards of all players starting the last to move before the current player, until someone can refute the hypothesis or it can't be refuted
	private void checkPlayersCards(ArrayList<Player> players, Hypothesis hypothesis, int playerTurn) {
		
		ArrayList<Card> playerCards;
		
		int suspect = hypothesis.getSuspect();
		int weapon = hypothesis.getWeapon();
		int room = hypothesis.getRoom();
		
		// Begin with most recent player to act before current player
		int i = playerTurn - 1;
		i = (i < 0) ? (i + players.size()) : i;
		
		// Check all players
		while (i != playerTurn) {
				
			playerCards = players.get(i).getCards();
				
			for(int j = 0; j < playerCards.size(); j++) {
					
				// Check if any card refutes the hypothesis on the basis of its suspect
				if (playerCards.get(j).getCardIndex() == suspect) {
					// Print result to screen and update notebooks
					System.out.println(players.get(i).getSuspectPawn().getName() + " refuted the hypothesis.");
					updateNotebook(players, players.get(playerTurn), players.get(i), playerCards.get(j), suspect, weapon, room);
					return;
				}
				// Check if any card refutes the hypothesis on the basis of its weapon
				else if (playerCards.get(j).getCardIndex() == weapon) {
					// Print result to screen and update notebooks
					System.out.println(players.get(i).getSuspectPawn().getName() + " refuted the hypothesis.");
					updateNotebook(players, players.get(playerTurn), players.get(i), playerCards.get(j), suspect, weapon, room);
					return;
				}
				// Check if any card refutes the hypothesis on the basis of its room
				else if (playerCards.get(j).getCardIndex() == room) {
					// Print result to screen and update notebooks
					System.out.println(players.get(i).getSuspectPawn().getName() + " refuted the hypothesis.");
					updateNotebook(players, players.get(playerTurn), players.get(i), playerCards.get(j), suspect, weapon, room);
					return;
				}
			}
			
			// Update player whose cards are to be checked
			i--;
			i = (i < 0) ? (i + players.size()) : i;
		}
		
		// If no card has been found that refutes hypothesis
		System.out.println("The hypothesis was not refuted");	
	}
	
	// checkPlayersCards() method
	// Purpose: Record info about the hypothesis in the notebooks of all players
	private void updateNotebook(ArrayList<Player> players, Player accuser, Player refuter, Card cardShown, int suspect, int weapon, int room) {
		
		// Two entries for accuser with details of card that refuted hypothesis
		accuser.getNotebook().addNote("I formulated the hypothesis " + Constants.GAME_LIST.get(suspect) + 
								 ", " + Constants.GAME_LIST.get(weapon) + ", " + Constants.GAME_LIST.get(room) + ".");
		accuser.getNotebook().addNote(refuter.getSuspectPawn().getName() + " refuted the hypothesis with the card " + cardShown.getName()+ ".");
		
		// Two entries for refuter
		refuter.getNotebook().addNote(accuser.getSuspectPawn().getName() + " formulated the hypothesis " + Constants.GAME_LIST.get(suspect) + 
				 ", " + Constants.GAME_LIST.get(weapon) + ", " + Constants.GAME_LIST.get(room) + ".");
		
		refuter.getNotebook().addNote("I refuted " + accuser.getSuspectPawn().getName() + "'s hypothesis with the card " + cardShown.getName()+ ".");	
		
		// Update notebooks of all players
		for (int i = 0; i < players.size(); i++) {
			
			// Accuser and refuter notebooks already updated
			if (i == refuter.getPlayerNumber() - 1 || i == accuser.getPlayerNumber() - 1) {
				continue;
				
			} else {
				players.get(i).getNotebook().addNote(accuser.getSuspectPawn().getName() + " made the hypothesis " + Constants.GAME_LIST.get(suspect) 
				+ ", " + Constants.GAME_LIST.get(weapon) + ", " + Constants.GAME_LIST.get(room) + ".");
				
				// Other players do not get info of card that refuted hypothesis
				players.get(i).getNotebook().addNote(refuter.getSuspectPawn().getName() + " refuted the hypothesis.");
			}				
		}
	}
}