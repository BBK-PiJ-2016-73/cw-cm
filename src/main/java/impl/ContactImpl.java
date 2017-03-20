package impl;
import java.util.ArrayList;
import java.util.Objects;

import spec.Contact;
import java.io.Serializable;

public class ContactImpl implements Contact, Serializable {

	private String contactName;
	private ArrayList<String> contactNotes;
	private static final long serialVersionUID = 1L;
	private int contactId;

	public ContactImpl(int id, String name) {
		
		if (id <= 0) {
			throw new IllegalArgumentException ("Error in contact: contact ID must not be zero or negative");
		}
		
		if (name.equals("")) {
			throw new IllegalArgumentException ("Error in contact: name cannot be empty" + "for contactId :" + id);
		}
		
		this.contactId = Objects.requireNonNull(id);
		this.contactName = Objects.requireNonNull(name);
		contactNotes = new ArrayList<>(); 
	}
	
	/**
	 * This constructor creates a new Contact with notes
	 * The most general constructor must have three parameters: int, String, String
	 * The first one corresponds to the ID provided by the ContactManager, the next one corresponds to the name,
	 * and the last one corresponds to the initial set of notes about the contact
	 * @param id, the ID provided by the Contact Manager
	 * @param name, the name of the contact
	 * @param notes, the initial set of notes about the contact
	 */
	public ContactImpl(int id, String name, String notes) {		
		this (id, name); 
		addNotes(notes); 
	}

	/**
	 * Returns the ID of the contact.
	 *
	 * @return the ID of the contact.
	 */
	@Override
	public int getId() {
		return contactId;
	}

	/**
	 * Returns the name of the contact.
	 *
	 * @return the name of the contact.
	 */
	@Override
	public String getName() {
		return contactName;
	}

	/**
	 * Returns our notes about the contact, if any.
	 * <p/>
	 * If we have not written anything about the contact, the empty
	 * string is returned.
	 *
	 * @return a string with notes about the contact, maybe empty.
	 */
	@Override
	public String getNotes() {
		String notesString = "";
		for (int i = 0; i <= contactNotes.size()-1; i++) {
			notesString = notesString + "\n" + contactNotes.get(i);
		}
		return notesString;
	}

	/**
	 * Add notes about the contact.
	 *
	 * @param note the notes to be added
	 */
	@Override
	public void addNotes(String note) {
		Objects.requireNonNull(note);
		if (note.equals("")) {
			throw new IllegalArgumentException ("Contract Id " + contactId + " for notes cannot be empty.");
		}
		contactNotes.add(note);
	}

}