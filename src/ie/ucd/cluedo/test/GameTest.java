package ie.ucd.cluedo.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ie.ucd.cluedo.Game;
import ie.ucd.cluedo.Player;

public class GameTest {

	Game testGame;
	ArrayList<Player> testArray;
	ArrayList<Player> testPlayers;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		testGame = new Game(3);
		testPlayers = new ArrayList<Player>();
		
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCreatePlayers() {

		testGame.createPlayers(3);
		
		assertEquals(1, testPlayers.get(0).getPlayerNumber());
		assertEquals(2, testPlayers.get(1).getPlayerNumber());
	}

//	@Test
//	public void testCreateCards() {
//		ArrayList<Card> testDeck = testGame.createCards();
//		assertEquals(18, testDeck.size());
//	}

//	@Test
//	public void testAllocateCards() {
//		testGame.createPlayers(3);
//		ArrayList<Card> testDeck = testGame.createCards();
//		testGame.allocateCards(testPlayers, testDeck);
//		
//		Card testCard;
//		testCard = testPlayers.get(1).getCards().get(1);
//		assertTrue(testCard instanceof Card);
//	}

}