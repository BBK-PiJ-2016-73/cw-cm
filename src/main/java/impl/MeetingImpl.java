package impl;

import java.util.Calendar;
import java.util.Objects;
import java.util.Set;
import spec.Contact;
import spec.Meeting;
import java.io.Serializable;


public abstract class MeetingImpl implements Meeting, Serializable {

	private static final long serialVersionUID = 1L;
	private int meetingID;
	private Calendar meetingDate;
	private Set<Contact> meetingContacts;
	
	/**
	* A class to represent meetings Constructs a Meeting
	 * @param meetingID the ID of the meeting (this class does not ensure uniqueness)
	 * @param meetingDate the date of the meeting using
	 * @param meetingContacts the Contacts for the people who attended the meeting - at least one
	 * 
	 * @throws NullPointerException if the date or contacts are null
	 * @throws IllegalArgumentException if the meetingID is negative or zero
	 * @throws IllegalArgumentException if the contacts are empty
	* Meetings have unique IDs, scheduled date and a list of participating contacts
	*/
	public MeetingImpl (int id, Calendar date, Set<Contact> contacts) {
		if (id <=0 ) {
			throw new IllegalArgumentException ("Meeting id must be greater than zero");
		}
			
		if (contacts.isEmpty()){
			throw new IllegalArgumentException ("At least one contact is required.");
		}
			
		Objects.requireNonNull(date);
		Objects.requireNonNull(contacts);
			
		this.meetingID = id;
		this.meetingDate = date;
		this.meetingContacts = contacts;
		}

	/**
	 * Returns the id of the meeting.
	 *
	 * @return the id of the meeting.
	 */
		@Override
		public int getId() {
			return meetingID;
		}

	/**
	 * Return the date of the meeting.
	 *
	 * @return the date of the meeting.
	 */
		@Override
		public Calendar getDate() {
			return meetingDate;
		}

	 /**
	 * Return the details of people that attended the meeting.
	 * <p/>
	 * The list contains a minimum of one contact (if there were
	 * just two people: the user and the contact) and may contain an
	 * arbitrary number of them.
	 *
	 * @return the details of people that attended the meeting.
	 */
		@Override
		public Set<Contact> getContacts() {
			return meetingContacts;
		}
	}