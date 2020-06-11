package ie.ucd.cluedo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Utility {

	public static void printCharactersAvailableToPlayer(int playerNumber) {	
		
		System.out.println("\nPlayer " + playerNumber + ", please select your character:\n1) MISS SCARLET\n2) PROFESSOR PLUM\n"
				+ "3) MRS. PEACOCK\n4) REVEREND MR. GREEN\n5) COLONEL MUSTARD\n6) MRS. WHITE\n");
	}
	
	public static void printSuccessMessage(ArrayList<Player> players , int playerNumber) {
		
		System.out.println("Player " + playerNumber + " is " + players.get(playerNumber-1).getSuspectPawn().getName() 
				+ " (" + Constants.COLOR_MAP.get(players.get(playerNumber-1).getSuspectPawn().getColor()) + ")");
	}
	
	public static void printFailureMessage() {
		
		System.out.println("Please enter an integer value between " + Constants.MIN_PLAYER_NUMBER + " and " + Constants.MAX_PLAYER_NUMBER + " (inclusive): ");
	}
	
	public static int getCharacterChoiceFromPlayer() {
		
		Scanner inputScanner = new Scanner(System.in);
			
		while (!inputScanner.hasNextInt()) {
				inputScanner.next();
				System.out.println("Please enter an integer value between " + Constants.MIN_PLAYER_NUMBER + " and " + Constants.MAX_PLAYER_NUMBER + " (inclusive): ");
		}		
		
		return inputScanner.nextInt();	
	}
	
	public static int throwDie() {
		return ThreadLocalRandom.current().nextInt(Constants.MIN_DIE_SCORE, Constants.MAX_DIE_SCORE + 1);
	}
}
