/***************************************************************/
/* Accusation Manager Class
/* 
/* Implements the formulation and resulting actions arising from 
/* accusations
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;
import java.util.Scanner;

public class AccusationManager {
	
	Accusation playerAccusation;
	
	public AccusationManager() {}	
	
	// simulateAccusation() Method
	// Purpose: Implements a full accusation and makes the necessary board repositions
	@SuppressWarnings("resource")
	public boolean simulateAccusation(ArrayList<Player> players, Board board, int playerTurn, boolean gameOver) {
		
		boolean winner;
		Scanner scanner = new Scanner(System.in);
		
		// Get suspect in accusation
		System.out.println("\nWhat suspect is in your accusation:");
		for (int i = 0; i < Constants.NUM_SUSPECTS; i++) {
			
			System.out.println(i + ")" + Constants.GAME_LIST.get(i));
		}
		
		int suspectAccusation = scanner.nextInt();
		
		
		// Get weapon in accusation
		System.out.println("\nWhat weapon is in your accusation:");
		for (int i = 0; i < Constants.NUM_WEAPONS; i++) {
			
			System.out.println(i + ")" + Constants.GAME_LIST.get(i + Constants.NUM_SUSPECTS));
		}
		
		int weaponAccusation = scanner.nextInt() + Constants.NUM_SUSPECTS;
		
		// Room in accusation is the players current room
		int currentRoom = players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber();
		int roomAccusation = currentRoom + Constants.NUM_SUSPECTS + Constants.NUM_WEAPONS - 1;
		
		// Create accusation object
		this.playerAccusation = new Accusation(suspectAccusation, weaponAccusation, roomAccusation);
		
		// Check if accusation is correct
		winner = checkEnvelope(players, this.playerAccusation, playerTurn);
		
		if (winner) {
			gameOver = true;
		} else {
			
			board.addSuspectPawn(players.get(playerTurn).getSuspectPawn());
			players.remove(playerTurn);
			
			// If there is only one player left, game is over and this player is the winner
			if (players.size() == 1) {
				System.out.println("There is only one player left.\n" + players.get(0).getSuspectPawn().getName() + " wins.\n");
				gameOver = true;
			}
			
			moveSuspectPawn(players, board, suspectAccusation, roomAccusation);
		}
		
		return gameOver;
	}
	
	// checkEnvelope() Method
	// Purpose: Checks if the accusation formulated is correct
	private boolean checkEnvelope(ArrayList<Player> players, Accusation accusation, int playerTurn) {
		
		int suspectAccusation = accusation.getSuspect();
		int weaponAccusation = accusation.getWeapon();
		int roomAccusation = accusation.getRoom();
	
		// If suspect is wrong, accusation is wrong and notebooks are updated
		if (suspectAccusation != Constants.murderSuspectIndex) {
			System.out.println("Sorry! You're accusation was wrong");
			updateNotebooks(players, players.get(playerTurn), suspectAccusation, weaponAccusation, roomAccusation);
			return false;
		}
		
		// If weapon is wrong, accusation is wrong and notebooks are updated
		else if (weaponAccusation != Constants.murderWeaponIndex) {
			System.out.println("Sorry! You're accusation was wrong");
			updateNotebooks(players, players.get(playerTurn), suspectAccusation, weaponAccusation, roomAccusation);
			return false;
		}
		
		// If room is wrong, accusation is wrong and notebooks are updated
		else if (roomAccusation != Constants.murderRoomIndex) {
			System.out.println("Sorry! You're accusation was wrong");
			updateNotebooks(players, players.get(playerTurn), suspectAccusation, weaponAccusation, roomAccusation);
			return false;
		}
		
		// Otherwise, the accusation is correct and the player wins
		else {
			System.out.println("You're accusation was correct!\nCongratulations" + players.get(playerTurn).getSuspectPawn().getName() 
					+ ", you are the winner");
			return true;
		}		
	}
	
	// moveSuspectPawn() Method
	// Purpose: Moves the suspect pawn of the of the suspect in the accusation to the room in the accusation 
	private void moveSuspectPawn(ArrayList <Player> players, Board board, int suspectAccusation, int roomAccusation) {
		
		// Checks if any of the players already have the suspect of the accusation, and if it's already in that room
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getSuspectPawn().getPawnIndex() == suspectAccusation) {
				if (players.get(i).getSuspectPawn().getPosition().getRoomNumber() == roomAccusation - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
					return;
				}
			}
		}
		
		// Checks if any of the suspect pawns that are unallocated to a player have the suspect of the accusation, and if it's in the accusation room
		for (int j = 0; j < board.getSuspectPawns().size(); j++) {
			
			if (board.getSuspectPawns().get(j).getPawnIndex() == suspectAccusation) {
				if (board.getSuspectPawns().get(j).getPosition().getRoomNumber() == roomAccusation - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
					return;
				}
			}
		}
		
		// Finds a free slot in the accusation room
		Slot roomSlot = getRoomSlot(players, board, roomAccusation);

		// If any of the players have the suspect, move it to roomSlot
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getSuspectPawn().getPawnIndex() == suspectAccusation) {	
				players.get(i).getSuspectPawn().movePosition(roomSlot);
				return;
			}
		}
		
		// Otherwise, the suspect pawn is not allocated to a player, so find it in the Board's pawn list and move it to roomSlot
		for (int j = 0; j < board.getSuspectPawns().size(); j++) {
			if (board.getSuspectPawns().get(j).getPawnIndex() == suspectAccusation) {
				board.getSuspectPawns().get(j).movePosition(roomSlot);
				return;
			}
		}
	}
	
	// getRoomSlot() Method
	// Purpose: Finds a free slot in the room of the accusation 
	private Slot getRoomSlot(ArrayList<Player> players, Board board, int roomAccusation) {
		
		boolean slotOccupied;
		
		ArrayList<RoomSlot> roomSlots = gameBoard.getRoomSlots();
		
		// Find all roomSlots that are in the room of the accusation
		for (RoomSlot rs: roomSlots) {
			
			slotOccupied = false;
			
			if (rs.getRoomNumber() == roomAccusation - Constants.NUM_SUSPECTS - Constants.NUM_WEAPONS + 1) {
				
				// Check if any of the players have suspect pawns in that room, if so, discard these slots
				for (Player p: players) {
					if (p.getSuspectPawn().getPosition() == gameBoard.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}
				
				// Check if any of the unallocated suspect pawns are in that room, if so, discard these slots
				for (int i = 0; i < board.getSuspectPawns().size(); i++) {
					if (board.getSuspectPawns().get(i).getPosition() == gameBoard.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}
				
				// If a slot passes the two above checks, return it as a viable slot
				if (!slotOccupied){
					return board.getSlots()[rs.getRow()][rs.getCol()];
				}
			}
		}
		return null;
	}
	
	// updateNotebooks() Method
	// Purpose: Updates the notebooks of all players about the accusation	
	private void updateNotebooks(ArrayList<Player> players, Player accuser, int suspectAccusation, int weaponAccusation, int roomAccusation) {
		
		for (int i = 0; i < players.size(); i++) {
			// No need to update accusers notebook as they are out of the game
			if (accuser.getPlayerNumber() == i + 1) {
				continue;
			} else { // Update all other players notebooks
				players.get(i).getNotebook().addNote(accuser.getSuspectPawn().getName() + " made the accustion " 
						+ Constants.GAME_LIST.get(suspectAccusation) + "," + Constants.GAME_LIST.get(weaponAccusation) + "," + Constants.GAME_LIST.get(roomAccusation) + ".");
			}
		}
	}	
}