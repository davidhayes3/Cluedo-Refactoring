/***************************************************************/ 
/* Notebook Class
/* 
/***************************************************************/

package ie.ucd.cluedo;

import java.util.ArrayList;

import javax.swing.*;


@SuppressWarnings("serial")
public class Notebook extends JFrame {
	
	JLabel label = new JLabel();
	String notes;
	
	public Notebook() {
		this.label = new JLabel();
		this.notes = "";
		this.setBounds(0, 0, 250, 250);
		this.setLocationRelativeTo(null);
		this.label.setVerticalAlignment(SwingConstants.TOP);
		this.setSize(500, 500);
		this.setTitle("Your Notebook");
		this.label.setText(this.notes);
		this.add(label);
	}
	
	public void addCards(ArrayList<Card> cards) {
		String newNote;
		for (int i = 0; i < cards.size(); i++) {
			newNote = cards.get(i).getName() + "\n";
			this.notes += newNote;
			updateLabel(newNote);
		}
	}
	
	// makeEntry() method
	public void addNote(String note) {
		String newNote = note + "\n";
		this.notes += newNote;
		updateLabel(newNote);
	}
	
	private void updateLabel(String newNote) {
		this.label.setText(label.getText() + "<html>" + newNote +"<br/><html>");
		this.label.setText(label.getText() +"\n");
	}
	
	// showNotebook() method
	public void viewNotebook() {
		this.setVisible(true);
	}
}