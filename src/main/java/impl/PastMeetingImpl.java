package impl;
/**
 * A meeting that was held in the past.
 *
 * It includes your notes about what happened and what was agreed.
 */
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

import spec.Contact;
import impl.MeetingImpl;
import spec.PastMeeting;


public class PastMeetingImpl extends MeetingImpl implements spec.PastMeeting {
	
	String meetingNotes;

	/**
	 * PastMeetingImpl
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		super (id, date, contacts);
		this.meetingNotes = notes; 
		Objects.requireNonNull(notes);
	}

	/**
	 * Returns the notes from the meeting.
	 *
	 * If there are no notes, the empty string is returned.
	 *
	 * @return the notes from the meeting.
	 */
	@Override
	public String getNotes() {
		if(meetingNotes != null) {
			return meetingNotes;
		}else {
			return null;
		}

	}

}