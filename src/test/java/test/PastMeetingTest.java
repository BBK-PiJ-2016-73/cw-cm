package test;

import impl.ContactImpl;
import impl.PastMeetingImpl;
import spec.Contact;
import spec.Meeting;
import spec.PastMeeting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;


public class PastMeetingTest {
	private Contact contact1, contact2, contact3;
	private int meetingID;
	private String meetingNotes, meetingNotes1, meetingNotes2;
	private Set<Contact> meetingContacts;
	private PastMeeting pastMeeting;
	private Calendar meetingDate;


	
	@Before
	public void setUp() {
		meetingID = 44;
		meetingDate = new GregorianCalendar(2017,03,05);
		contact1 = new ContactImpl(445, "Birthday Month", "It will be my birthday soon");
		contact2 = new ContactImpl(446, "Party time", "Day schedule to book a hall");
		contact3 = new ContactImpl(447, "Setup Party organisers", "Calculate cost of hiring a hall for party");

		meetingContacts = new HashSet<>();
		meetingContacts.add(contact1);
		meetingContacts.add(contact2);
		meetingContacts.add(contact3);
		
		meetingNotes1 = "Ready or not";
		meetingNotes2 = "The exam time is near";
		meetingNotes = meetingNotes1.concat(" ").concat(meetingNotes2);
		pastMeeting = new PastMeetingImpl(meetingID, meetingDate, meetingContacts, meetingNotes);
	}



	@Test
	public void createPastMeeting() {
		assertNotNull(pastMeeting);
	}
	

	@Test
	public void testPastMeetingID() {
		assertEquals(meetingID, pastMeeting.getId());
	}
	

	@Test
	public void testPastMeetingDate() {
		assertEquals(meetingDate, pastMeeting.getDate());
	}
	
	@Test
	public void testPastMeetingDelegates() {
		assertEquals(meetingContacts, pastMeeting.getContacts());
	}

	@Test
	public void testPastMeetingNotes() {
		assertEquals(meetingNotes, pastMeeting.getNotes());
	}

	@Test
	public void testEqualsSame() {
		int meetingId2 = 444;
		Calendar date2 = new GregorianCalendar (2017,03,05);
		String meetingNotes = "Ready or not The exam time is near";
		
		PastMeeting pastMeeting2 = new PastMeetingImpl(meetingId2, date2, meetingContacts, meetingNotes);
		assertTrue(pastMeeting.getId()==(meetingId2));
		assertTrue(pastMeeting.getDate().equals(date2));
		assertTrue(pastMeeting.getContacts().equals(meetingContacts));
		assertTrue(pastMeeting.getNotes().equals(meetingNotes));
	}

	@Test
	public void testEqualsDifferentNotes() {
		int meetingId2 = 444;
		Calendar date2 = new GregorianCalendar (2017,03,05);
		meetingNotes2 = "You have face the exam";
		PastMeeting pastMeeting2 = new PastMeetingImpl(meetingId2, date2, meetingContacts, meetingNotes2);
		assertFalse(pastMeeting2.equals(pastMeeting));
	}
	
	@Test (expected = NullPointerException.class)
	public void nullDateThrowsException() {
		new PastMeetingImpl(meetingID, null, meetingContacts, meetingNotes);
	}

	
	@Test (expected = NullPointerException.class)
	public void nullContactsThrowsException() {
		new PastMeetingImpl(meetingID, meetingDate, null, meetingNotes);
	}
	

	@Test (expected = NullPointerException.class)
	public void nullNotesThrowsException() {
		new PastMeetingImpl(meetingID, meetingDate, meetingContacts, null);
	}

	@Test (expected = NullPointerException.class)
	public void nullDateAndContactsThrowsException() {
		new PastMeetingImpl(meetingID, null, meetingContacts, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullContactsAndNotesThrowsException() {
		new PastMeetingImpl(meetingID, meetingDate, null, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullDateAndContactsAndNotesThrowsException() {
		new PastMeetingImpl(meetingID, null, null, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativeIdThrowsException() {
		new PastMeetingImpl(-1, meetingDate, meetingContacts, meetingNotes);
	}

	
	@Test (expected = IllegalArgumentException.class)
	public void zeroIdThrowsException() {
		new PastMeetingImpl(0, meetingDate, meetingContacts, meetingNotes);
	}

	@Test (expected = IllegalArgumentException.class)
	public void emptyContactsThrowsException() {
		Set<Contact> emptyContacts = new HashSet<>();
		new PastMeetingImpl(meetingID, meetingDate, emptyContacts, meetingNotes);
	}
	
	@After
	public void tearDown() {
		meetingContacts = null;
		meetingNotes = null;
		contact1 = null;
		contact2 = null;
		contact3 = null;
		meetingID = 0;
		meetingDate = null;

	}
}