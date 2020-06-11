package ie.ucd.cluedo;

import java.util.concurrent.ThreadLocalRandom;

public class Die {

	public Die() {}
	
	public int rollDies() {
		return ThreadLocalRandom.current().nextInt(Constants.MIN_DIE_SCORE, Constants.MAX_DIE_SCORE + 1);
	}
}