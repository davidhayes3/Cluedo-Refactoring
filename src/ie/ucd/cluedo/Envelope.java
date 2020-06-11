package ie.ucd.cluedo;

public class Envelope {
	
	Card suspect;
	Card weapon;
	Card room;
	
	public Envelope() {}
	
	public void fillEnvelope(Card suspect, Card weapon, Card room) {
		this.suspect = suspect;
		this.weapon = weapon;
		this.room = room;
	}
}