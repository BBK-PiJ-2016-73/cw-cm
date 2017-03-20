package impl;

import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;

import spec.Contact;
import spec.FutureMeeting;
import impl.MeetingImpl;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * A class to represent meetings
	 *
	 * Meetings have unique IDs, scheduled date and a list of participating contacts
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		super (id, date, contacts);
	}
}