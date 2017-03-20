package impl;
import spec.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileInputStream;

//import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.*;


public class ContactManagerImpl implements spec.ContactManager {
	
	private int contactId;
	private int meetingId;
	private Calendar contactManagerDate; 
	private Set<spec.Contact> contacts; 
	private List<spec.Meeting> meetings; 
	private static final String fileName = "contacts.txt";

	public ContactManagerImpl() {	
		createDeleteFile();
		
	}

	/**
	 * Add a new meeting to be held in the future.
	 *
	 * An ID is returned when the meeting is put into the system. This ID must be positive and non-zero.
	 *
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param  date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past,
	 *                                  of if any contact is unknown / non-existent
	 * @throws NullPointerException if the meeting or the date are null
	 */
	@Override
	public int addFutureMeeting(Set<spec.Contact> contacts, Calendar date) {
		
		Objects.requireNonNull(contacts);
		Objects.requireNonNull(date);
		
		if (date.before(Calendar.getInstance())) {
			throw new IllegalArgumentException("Invalid future meeting. All meetings cannot be held in the past."); 
		}
		
		if (!contacts.containsAll(contacts)) {
			throw new IllegalArgumentException("Please add contact in the contact manager.");
		}
		
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getContacts().contains(contacts) && m.getDate()==date && m instanceof spec.FutureMeeting)
				.collect(Collectors.toList()); 
		if (!(meetingstream.isEmpty())) {
			return meetingstream.get(0).getId(); 
		} else {
			meetingId = updateMeetingId();
			spec.FutureMeeting newFutureMeeting = new FutureMeetingImpl(meetingId, date, contacts);
			Objects.requireNonNull(newFutureMeeting);
			meetings.add(newFutureMeeting);
			return meetingId;
		}
	}

	/**
	 * Returns the PAST meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 * @throws IllegalStateException if there is a meeting with that ID happening in the future
	 */
	@Override
	public spec.PastMeeting getPastMeeting(int id) {
	
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getId() == id)
				.collect(Collectors.toList());
		if(!(meetingstream.isEmpty())) {
			spec.Meeting meeting = meetingstream.get(0);
				if (!(meeting instanceof spec.PastMeeting)) {
					throw new IllegalStateException("This meeting is not a past meeting");
				}
			return (spec.PastMeeting) meeting;
		} else { 
			return null;
		}
	}

	/**
	 * Returns the FUTURE meeting with the requested ID, or null if
	 * there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none.
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 */
	@Override
	public spec.FutureMeeting getFutureMeeting(int id) {
		
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getId() == id)
				.collect(Collectors.toList());
		if(!(meetingstream.isEmpty())) {
			spec.Meeting meeting = meetingstream.get(0);
				if (!(meeting instanceof spec.FutureMeeting)) {
					throw new IllegalArgumentException("This meeting is not a future meeting");
				}
			return (spec.FutureMeeting) meeting;
		} else { 
			return null;
		}
	}

	/**
	 * Returns the meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none.
	 */
	@Override
	public spec.Meeting getMeeting(int id) {
		
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getId() == id)
				.collect(Collectors.toList());
		if(!(meetingstream.isEmpty())) {
			spec.Meeting meeting = meetingstream.get(0);
			return meeting;
		} else {
		return null;
		}
	}

	/**
	 * Returns the list of future meetings scheduled with this contact.
	 * <p/>
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any
	 * duplicates.
	 *
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty).
	 * @throws IllegalArgumentException if the contact does not exist
	 * @throws NullPointerException if the contact is null
	 *
	 */
	@Override
	public List<spec.Meeting> getFutureMeetingList(spec.Contact contact) {
		
		Objects.requireNonNull(contact);
		
		if (!contacts.contains(contact)) throw new IllegalArgumentException("The contact must be in the Contact Manager");
		
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getContacts().contains(contact) && m instanceof spec.FutureMeeting)
				.sorted(Comparator.comparing(spec.Meeting::getDate)) 
				.distinct()
				.collect(Collectors.toList());
			return meetingstream;		
		}

	/**
	 * Returns the list of past meetings in which this contact has participated.
	 * <p/>
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any
	 * duplicates.
	 *
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty).
	 * @throws IllegalArgumentException if the contact does not exist
	 * @throws NullPointerException if the contact is null
	 */
	@Override
	public List<spec.Meeting> getMeetingListOn(Calendar date) {
		
		Objects.requireNonNull(date);
		
		List<spec.Meeting> meetingstream = meetings.stream()
				.filter(m -> m.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) && 
						m.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
						m.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
				.sorted(Comparator.comparing(spec.Meeting::getDate))
				.distinct()
				.collect(Collectors.toList());
		return meetingstream;
	}

	/**
	 * Create a new record for a meeting that took place in the past.
	 *
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text - messages to be added about the meeting.
	 * @throws IllegalArgumentException if the list of contacts is
	 *                                  empty, or any of the contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 */
	@Override
	public List<spec.PastMeeting> getPastMeetingListFor(spec.Contact contact) {
		
		Objects.requireNonNull(contact);
		
		if (!contacts.contains(contact)) throw new IllegalArgumentException("The contact must be in the Contact Manager");
		
		List<spec.PastMeeting> meetingstream = meetings.stream()
				.filter(m -> m.getContacts().contains(contact) && m instanceof spec.PastMeeting)
				.sorted(Comparator.comparing(spec.Meeting::getDate))
				.map(m -> (spec.PastMeeting) m)
				.distinct()
				.collect(Collectors.toList());
		
		return meetingstream;
	}
	/**
	 * Add notes to a meeting.
	 * <p/>
	 * This method is used when a future meeting takes place, and is
	 * then converted to a past meeting (with notes).
	 * <p/>
	 * It can be also used to add notes to a past meeting at a later date.
	 *
	 * @param id the ID of the meeting
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null
	 */
	@Override
	public void addNewPastMeeting(Set<spec.Contact> contacts, Calendar date, String text) {
		Objects.requireNonNull(contacts);
		Objects.requireNonNull(date);
		Objects.requireNonNull(text);
		
		contactManagerDate = Calendar.getInstance();
		
		if (!contacts.containsAll(contacts)) throw new IllegalArgumentException("The contacts you entered do not exist in the ContactManager");
		if (contacts.isEmpty()) throw new IllegalArgumentException("There must be at least one contact for the meeting");
		if (date.after(contactManagerDate)) throw new IllegalArgumentException("A past meeting cannot take place in the future");
		
		meetingId = updateMeetingId();
		spec.PastMeeting newPastMeeting = new PastMeetingImpl(meetingId, date, contacts, text);
		meetings.add(newPastMeeting);
	}
	/**
	 * Create a new contact with the specified name and notes.
	 *
	 * @param name  the name of the contact.
	 * @param notes notes to be added about the contact
	 * @return the ID for the new contact
	 * @throws IllegalArgumentException if the name or the notes are empty strings
	 * @throws NullPointerException if the name or the notes are null
	 */
	@Override
	public spec.PastMeeting addMeetingNotes(int id, String text) {
		if (text == null) {
			throw new NullPointerException("The notes cannot be null.");
		}

		spec.Meeting meeting = this.getMeeting(id);
		if (meeting == null) {
			throw new IllegalArgumentException("A meeting id: '" + id + "' does not exist.");
		}
		Calendar now = Calendar.getInstance();
		if (meeting.getDate().after(now)) {

			throw new IllegalStateException("The selected meeting exists but is a future meeting. Please supply a past meeting id.");
		}
		this.meetings.remove(meeting.getId());
		spec.Meeting pastMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), text);

		this.meetings.add(pastMeeting.getId(), pastMeeting);
		return (spec.PastMeeting) pastMeeting;
	}

	/**
	 * Create a new contact with the specified name and notes.
	 *
	 * @param name  the name of the contact.
	 * @param notes notes to be added about the contact
	 * @return the ID for the new contact
	 * @throws IllegalArgumentException if the name or the notes are empty strings
	 * @throws NullPointerException if the name or the notes are null
	 */
	@Override
	public int addNewContact(String name, String notes) {
		
		Objects.requireNonNull(name);
		Objects.requireNonNull(notes);
		if (name.equals("")|| notes.equals("")) throw new IllegalArgumentException("Please enter a name and notes for the contact");
		
		int id = updateContactId(); 
		spec.Contact newContact = new ContactImpl(id, name, notes);
		contacts.add(newContact);
		return id;
	}
	private void createDeleteFile() {
	    	
	         if (isFileExists()) {
	        	 try {
	        		 ObjectInputStream objectIn = new ObjectInputStream 
	        				 						(new BufferedInputStream
	        				 								(new FileInputStream(fileName)));
	        		 meetings = (List<spec.Meeting>) objectIn.readObject();
	        		 contacts = (Set<spec.Contact>) objectIn.readObject();
	        		 contactId = (int) objectIn.readObject();
	        		 meetingId = (int) objectIn.readObject();
	        		 contactManagerDate = (Calendar) objectIn.readObject();
	        		 objectIn.close();
	        	 } catch (IOException | ClassNotFoundException e) {
	        		 e.printStackTrace();
	        	 }	 
	         } else {
	        	 meetingId = 0;
	        	 contactId = 0;
	        	 contacts = new HashSet<>();
	        	 meetings = new ArrayList<>();
	        	 contactManagerDate = Calendar.getInstance();
	         }
	}

	/**
	 * @see spec.ContactManager#getContacts(String name)
	 * @throws NullPointerException if the name passed as parameter is null
	 * This method creates a new set called searchResults
	 * It iterates over the set looking for the specified string converted to lower case
	 * Once the name is returned, a new contact with the specified id, name and notes is
	 * added to the searchResults set
	 */
	@Override
	public Set<spec.Contact> getContacts(String name) {
		
		Objects.requireNonNull(name);
		if (name.equals("")) {
			return contacts.stream().collect(Collectors.toCollection(HashSet::new));
		} else {
			Set<spec.Contact> searchResults = contacts.stream()
				.filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toCollection(HashSet::new));
		return searchResults;
		}
	}

	/**
	 * Returns a list containing the contacts that correspond to the IDs.
	 * Note that this method can be used to retrieve just one contact by passing only one ID.
	 *
	 * @param ids an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs.
	 * @throws IllegalArgumentException if no IDs are provided or if any of the IDs does not correspond to a real contact
	 */
	@Override
	public Set<spec.Contact> getContacts(int... ids) { // int... ids means an array of ints
		
		if (ids.length==0) throw new IllegalArgumentException("Please enter an id of the contact you wish to find");
		
		Set<spec.Contact> contactStream = contacts.stream()
				.filter(c -> Arrays.stream(ids).anyMatch(id -> id == c.getId()))
				.collect(Collectors.toCollection(HashSet::new));
		
		if (contactStream.size() == 0) throw new IllegalArgumentException("The id you entered could not be found");
		
		return contactStream;
	}
	/**
	 * Save all data to disk.
	 * <p/>
	 * This method must be executed when the program is
	 * closed and when/if the user requests it.
	 */
	@Override
	public void flush() {
		try {
			if (this.isFileExists()) {
				File file = new File(this.fileName);
				file.delete();
			}
			FileOutputStream file = new FileOutputStream(this.fileName);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(this.getMeetings());
			output.writeObject(this.getContacts());
			output.close();
			file.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Returns the id of the contact
	 * @return the id of the contact
	 */
	@Override
	public int getContactId() {
		return contactId;
	}

	/**
	 * Returns the id of the meeting
	 * @return the id of the meeting
	 */
	@Override
	public int getMeetingId() {
		return meetingId;
	}

	/**
	 * Returns the list of contacts in the ContactManager
	 * @return contacts in the ContactManager of type Set<Contact>
	 */
	@Override
	public Set<spec.Contact> getContacts() {
		return contacts;
	}

	/**
	 * Returns the list of meetings in the ContactManager
	 * @return meetings in the ContactManager of type List<Meeting>
	 */
	@Override
	public List<spec.Meeting> getMeetings() {
		return meetings;
	}

	/**
	 * Increases the contactId by one when a contact is added to the ContactManager.
	 * @return the id of the added Contact
	 */
	@Override
	public int updateContactId() {
		contactId++;
		return contactId;
	}

	/**
	 * Increases the meetingId by one when a contact is added to the ContactManager.
	 * @return the Id of the added Meeting
	 */
	@Override
	public int updateMeetingId() {
		meetingId++;
		return meetingId;
	}
   private boolean isFileExists() {
		Path p = FileSystems.getDefault().getPath(fileName);
		File file = new File(p.toFile().getName());

		return file.exists();

	}



}