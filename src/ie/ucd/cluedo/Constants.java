/***************************************************************/
/* Game Values Class
/* 
/* Contains all enumeration types and constants for entire 
/* project and generates the murder combination
/***************************************************************/

package ie.ucd.cluedo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class Constants {
	
	// Enumeration type for cards
	// why not static
	public enum CardType {
		SUSPECT, WEAPON, ROOM;
	}

	// Enumeration type for pawns
	public enum PawnType {
		SUSPECT, WEAPON;
	}

	// Enumeration type for names of suspects, weapons and rooms
	public enum Name {
		MISS_SCARLET, PROFESSOR_PLUM, MRS_PEACOCK, REVEREND_MR_GREEN, COLONEL_MUSTARD, MRS_WHITE,
		CANDLE_STICK, KNIFE, LEAD_PIPE, REVOLVER, ROPE, POISON,
		KITCHEN, BALLROOM, CONSERVATORY, DINING_ROOM, LOUNGE, HALL, STUDY, BILLIARD_ROOM, LIBRARY;
	}
	
	// List of all possible suspects, weapons and rooms 
	public static final ArrayList<Name> GAME_LIST = new ArrayList<Name>(EnumSet.allOf(Name.class));
	
	// List of constants needed for logic
	public static final int MIN_NUM_PLAYERS = 2;
	public static final int MAX_NUM_PLAYERS = 6;
	public static final int MIN_PLAYER_NUMBER = 1;
	public static final int MAX_PLAYER_NUMBER = 6;
	public static final int MIN_DIE_SCORE = 2;
	public static final int MAX_DIE_SCORE = 12;
	public static final int NUM_SUSPECTS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PAWNS = 12;
	public static final int NUM_CARDS_IN_DECK = 21;
	public static final int NUM_MURDER_CARDS = 3;
	public static final int NUM_CARDS_IN_PLAY = NUM_CARDS_IN_DECK - NUM_MURDER_CARDS;
	
	// List of constants needed for board
	public static final int BOARD_WIDTH = 24;
	public static final int BOARD_HEIGHT = 24;
	public static final int BUTTON_PIXEL_WIDTH = 21;
	public static final int BUTTON_PIXEL_HEIGHT = 21;
	public static final int KITCHEN_ROOM_NUMBER = 1;
	public static final int CONSERVATORY_ROOM_NUMBER = 3;
	public static final int LOUNGE_ROOM_NUMBER = 5;
	public static final int STUDY_ROOM_NUMBER = 7;
	
	// SHOULD NOT BE IN CONSTANTS FILE
	// Select the murder details at random
	public static final int murderSuspectIndex = ThreadLocalRandom.current().nextInt(0, NUM_SUSPECTS);
	public static final int murderWeaponIndex = ThreadLocalRandom.current().nextInt(NUM_SUSPECTS, NUM_SUSPECTS + NUM_WEAPONS);
	public static final int murderRoomIndex = ThreadLocalRandom.current().nextInt(NUM_SUSPECTS + NUM_WEAPONS, NUM_SUSPECTS + NUM_WEAPONS + NUM_ROOMS);
	public static final Name murderSuspect = GAME_LIST.get(murderSuspectIndex);
	public static final Name murderWeapon = GAME_LIST.get(murderWeaponIndex);
	public static final Name murderRoom = GAME_LIST.get(murderRoomIndex);
	
	// Color associated to each pawn
	public static final Color[] PAWN_COLORS = {Color.RED, new Color(192, 0, 255), Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE};
	
	// 
	public static final Map<Color, String> COLOR_MAP = new HashMap<Color, String>() {	
		private static final long serialVersionUID = -5354969041589404582L; {
			put(Color.RED, "Red");  
		    put(new Color(192, 0, 255), "Purple");
		    put(Color.BLUE, "Blue");
		    put(Color.GREEN, "Green");
		    put(Color.ORANGE, "Orange");
		    put(Color.WHITE, "White");
		}
	};		
}