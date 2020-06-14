/***************************************************************/
/* MoveType Class
/* 
/* Implements the types of move possible on the board 
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;

public class MoveType {
	
	public MoveType() {}
	
	// normalRoomMove() Method
	// Purpose: Implements possible actions when in a room
	public int normalRoomMove(final ArrayList<Player> players, final int playerTurn, final Board board,
			int movesRemaining, final String playerChoice) {

		switch (playerChoice) {

			case "l": // Choosing to leave moves the player to the rooms door without taking a move
						// away
				final Slot doorSlot = getDoorSlot(
						players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board);
				players.get(playerTurn).getSuspectPawn().movePosition(doorSlot);

				break;

			case "s": // Choosing to stay ends the players movement for that turn
				movesRemaining = 0;
				break;

			default:
				break;

		}

		return movesRemaining;
	}

	// secretRoomMove() Method
	// Purpose: Implements possible actions when in a room with a secret passage
	public int secretRoomMove(final ArrayList<Player> players, final int playerTurn, final Board board,
			int movesRemaining, final String playerChoice) {

		switch (playerChoice) {

			case "l":
				final Slot doorSlot = getDoorSlot(
						players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board);
				players.get(playerTurn).getSuspectPawn().movePosition(doorSlot);

				break;

			case "s":
				movesRemaining = 0;
				break;

			case "p": // Moves player to room at other side of secret passage
				final Slot secretSlot = getSecretSlot(
						players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board, players);
				players.get(playerTurn).getSuspectPawn().movePosition(secretSlot);

				movesRemaining--;

			default:
				break;
		}

		return movesRemaining;
	}

	// boardMove() Method
	// Purpose: Implements possible actions when in the corridor
	public int boardMove(final ArrayList<Player> players, final int playerTurn, final Board board, int movesRemaining,
			final String playerChoice) {

		int newCol, newRow;
		boolean canMove;
		final Slot currentPosition = players.get(playerTurn).getSuspectPawn().getPosition();

		switch (playerChoice) {

			case "u":
				newRow = currentPosition.getRow() - 1;
				newCol = currentPosition.getCol();

				// Checks if player can move to indicated position of preference
				canMove = canMove(currentPosition, newCol, newRow, board, players);

				// If the players intended move is legal, move the player
				if (canMove) {
					players.get(playerTurn).getSuspectPawn().movePosition(board.getSlots()[newRow][newCol]);
					movesRemaining--;
				}

				// If player moves on to a board button, move the player to a room slot within
				// that room
				if (players.get(playerTurn).getSuspectPawn().getPosition() instanceof DoorSlot) {

					final Slot roomSlot = getRoomSlot(
							players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board, players);
					players.get(playerTurn).getSuspectPawn().movePosition(roomSlot);
				}

				return movesRemaining;

			case "d":
				newRow = currentPosition.getRow() + 1;
				newCol = currentPosition.getCol();

				canMove = canMove(currentPosition, newCol, newRow, board, players);

				if (canMove) {
					players.get(playerTurn).getSuspectPawn().movePosition(board.getSlots()[newRow][newCol]);
					movesRemaining--;
				}

				if (players.get(playerTurn).getSuspectPawn().getPosition() instanceof DoorSlot) {
					final Slot roomSlot = getRoomSlot(
							players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board, players);
					players.get(playerTurn).getSuspectPawn().movePosition(roomSlot);
				}

				return movesRemaining;

			case "l":
				newRow = currentPosition.getRow();
				newCol = currentPosition.getCol() - 1;

				canMove = canMove(currentPosition, newCol, newRow, board, players);

				if (canMove) {
					players.get(playerTurn).getSuspectPawn().movePosition(board.getSlots()[newRow][newCol]);
					movesRemaining--;
				}

				if (players.get(playerTurn).getSuspectPawn().getPosition() instanceof DoorSlot) {

					final Slot roomSlot = getRoomSlot(
							players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board, players);
					players.get(playerTurn).getSuspectPawn().movePosition(roomSlot);
				}

				return movesRemaining;

			case "r":
				newRow = currentPosition.getRow();
				newCol = currentPosition.getCol() + 1;

				canMove = canMove(currentPosition, newCol, newRow, board, players);

				if (canMove) {
					players.get(playerTurn).getSuspectPawn().movePosition(board.getSlots()[newRow][newCol]);
					movesRemaining--;
				}

				if (players.get(playerTurn).getSuspectPawn().getPosition() instanceof DoorSlot) {
					final Slot roomSlot = getRoomSlot(
							players.get(playerTurn).getSuspectPawn().getPosition().getRoomNumber(), board, players);
					players.get(playerTurn).getSuspectPawn().movePosition(roomSlot);
				}

				return movesRemaining;

			case "f": // If the player wants to finish moving without using all its possible moves
				movesRemaining = 0;
				return movesRemaining;

			default:
				System.out.println("Please enter a valid option");
				return movesRemaining;
		}
	}

	// canMove() Method
	// Purpose: Implements possible actions when in the corridor
	public boolean canMove(Slot currentPosition, int newCol, int newRow, Board board, ArrayList<Player> players) {

		// If desired position is not on board
		if (newRow < 0 || newRow > Constants.BOARD_HEIGHT - 1 || newCol < 0 || newCol > Constants.BOARD_WIDTH - 1) {
			System.out.println("That position is not on the board.");
			return false;

		} else if (board.getSlots()[newRow][newCol] == null || board.getSlots()[newRow][newCol] instanceof RoomSlot) { 
			// If player tries to access room other than through a door
			// If not from a door slot, print illegal move
			if (!(currentPosition instanceof DoorSlot)) {
				System.out.println("Cannot access a room through a wall.");
			}

			return false;

		} else { // If there's already a suspect pawn at the desired position

			for (final Player p : players) {
				if (p.getSuspectPawn().getPosition() == board.getSlots()[newRow][newCol]) {
					System.out.println("There's already a player at that position.");
					return false;
				}
			}

			//for (final SuspectPawn s : board.getSuspectPawns()) {
			for (final SuspectPawn s : board.getSuspectPawns()) {
				if (s.getPosition() == board.getSlots()[newRow][newCol]) {
					System.out.println("There's already a pawn at that position.");
					return false;
				}
			}
		}

		return true;
	}

	// getSecretSlot() Method
	// Purpose: Finds a free slot in the room at the other side of the secret
	// passage
	private Slot getSecretSlot(final int roomNumber, final Board board, final ArrayList<Player> players) {

		// Method can only be called from 4 rooms with a secret passage, so only 4
		// possible values of roomNumber
		switch (roomNumber) {

			case 1:
				return getRoomSlot(Constants.STUDY_ROOM_NUMBER, board, players);

			case 3:
				return getRoomSlot(Constants.LOUNGE_ROOM_NUMBER, board, players);

			case 5:
				return getRoomSlot(Constants.CONSERVATORY_ROOM_NUMBER, board, players);

			case 7:
				return getRoomSlot(Constants.KITCHEN_ROOM_NUMBER, board, players);

			default:
				return null;
		}
	}

	// getRoomSlot() Method
	// Purpose: Finds a free slot in the room the player is trying to enter
	private Slot getRoomSlot( int roomNumber, Board board, ArrayList<Player> players) {

		boolean slotOccupied;
		final ArrayList<RoomSlot> roomSlots = board.getRoomSlots();

		for (final RoomSlot rs : roomSlots) {
			slotOccupied = false;

			if (rs.getRoomNumber() == roomNumber) {

				// Check if any players have suspect pawns at the slot in question. If so,
				// discard slot
				for (final Player p : players) {
					if (p.getSuspectPawn().getPosition() == board.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}

				// Check if any remaining suspect pawns are at the slot in question. If so,
				// discard slot
				//for (final SuspectPawn s : board.getSuspectPawns()) {
				for (final SuspectPawn s : board.getSuspectPawns()) {
					if (s.getPosition() == board.getSlots()[rs.getRow()][rs.getCol()]) {
						slotOccupied = true;
						break;
					}
				}

				// Return the first free slot in room found
				if (!slotOccupied) {
					return board.getSlots()[rs.getRow()][rs.getCol()];
				}
			}
		}

		return null;
	}

	// getDoorSlot() Method
	// Purpose: Finds the slot of the door for a given room
	private Slot getDoorSlot(final int roomNumber, final Board board) {

		final ArrayList<DoorSlot> doorSlots = board.getDoorSlots();

		for (final DoorSlot ds : doorSlots) {
			
			if (ds.getRoomNumber() == roomNumber) {
				return board.getSlots()[ds.getRow()][ds.getCol()];
			}
		}
		
		return null;
	}	
}