/***************************************************************/
/* PlayerHand Class
/* 
/* Contains the cards allocated to a player
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//@SuppressWarnings("serial")
public class CardView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JLabel label = new JLabel();
	ArrayList<Card> cards;
		
	public CardView(ArrayList<Card> cards) {
		
		this.cards = cards;
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(label);
		this.setTitle("Your Cards");
		label.setVerticalAlignment(SwingConstants.TOP);
		
		for(int i = 0; i < this.cards.size(); i++) {	
			String string = this.cards.get(i).getName().toString();
			label.setText(label.getText() + "<html>" + string +"<br/><html>");
		}
	}
	
	// showHand() method, makes JFrame visible
	public void viewCards() {
		this.setVisible(true);
	}
}