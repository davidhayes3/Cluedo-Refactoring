/***************************************************************/
/* Game Manager Class
/* 
/* Implements full game of game
/***************************************************************/

package ie.ucd.cluedo;

import java.util.Scanner;

public class GameManager {
	
	Boolean isSessionTerminated;
	
	public GameManager() {	
		
		this.isSessionTerminated = false;
		int numPlayers;
		
		do {
			numPlayers = getNumPlayers();
			
			Game game = new Game(numPlayers);
			game.simulatePlayerTurns();
			
			this.isSessionTerminated = !askUserToStartNewGame();
			
		} while (!this.isSessionTerminated);
	}
	
	// getNumPlayers() Method
	// Purpose: Repeatedly asks users for input until an integer between 2 and 6 is chosen, and then returns this value
	@SuppressWarnings("resource")
	private int getNumPlayers() {	

		int numPlayers;
		
		System.out.println("Welcome to game. Please enter the number of players (2-6):");
		
		// Ask user for input until number of players between 2 and 6 is selected
		while (true) {
			
			Scanner scanner = new Scanner(System.in);
			
			// Wait until an integer value is input
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("Please enter an integer value between 2 and 6 (inclusive):");
			}
			
			numPlayers = scanner.nextInt();
				
			if (numPlayers >= Constants.MIN_NUM_PLAYERS && numPlayers <= Constants.MAX_NUM_PLAYERS) {
				break;
			} else {
				System.out.println("Please enter an integer value between 2 and 6 (inclusive)");
			}
		}
		
		return numPlayers;	
	}
	
	private boolean askUserToStartNewGame() {
		
		boolean newGame = false;
		
		System.out.printf("\nWould you like to play again? [y/n]\nOption: ");
		Scanner input = new Scanner(System.in);
		try {
			String response = input.nextLine();			
			if (response.equals('y')) {
				newGame = true;
			}
		} finally {
	        input.close();
	    }	
		
		return newGame;
	}
}