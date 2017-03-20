package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import spec.Contact;
import spec.Meeting;
import impl.ContactImpl;

public class MeetingTest {
	private Set<Contact> meetingDelegates;
	private Meeting mockMeeting;
	private int meetingID;
	private Calendar meetingDate;

	Contact contact1 = new ContactImpl(445, "Birthday Month", "It will be my birthday soon");
	Contact contact2 = new ContactImpl(446, "Party time", "Day schedule to book a hall");
	Contact contact3 = new ContactImpl(447, "Setup Party organisers", "Calculate cost of hiring a hall for party");

	@Before
	public void setUp() {
		meetingID = 1;
		meetingDate = new GregorianCalendar(1988,06,06);
		meetingDelegates = new HashSet<>();
		meetingDelegates.add(contact1);
		meetingDelegates.add(contact2);
		meetingDelegates.add(contact3);
	
		mockMeeting = new MeetingMock(meetingID, meetingDate, meetingDelegates);
	}	

	@Test
	public void testMeetingID() {
		assertEquals(1, mockMeeting.getId());
	}



	@Test
	public void testMeetingDate() {
		assertEquals (new GregorianCalendar(1988,06,06), mockMeeting.getDate());
	}



	@Test
	public void testMeetingDelegates() {
		assertEquals (meetingDelegates, mockMeeting.getContacts());
	}


	@Test
	public void testContactsEqualSame() {	
		Set<Contact> testDelegates = new HashSet<>();
		testDelegates.add(contact1);
		testDelegates.add(contact2);
		testDelegates.add(contact3);
		assertTrue(testDelegates.equals(mockMeeting.getContacts()));
	}	

	@Test
	public void testIDEqualsSame() {
		int testMeetingID = 1;
		assertTrue(testMeetingID == mockMeeting.getId());
	}



	@Test 
	public void testMeetingDateEqualsSame() {
		Calendar testDate = new GregorianCalendar(1988,06,06);
		assertTrue(testDate.equals(mockMeeting.getDate()));
	}


	@Test (expected = NullPointerException.class)
	public void nullDateThrowsException() {
		mockMeeting = new MeetingMock(meetingID, null, meetingDelegates);
	}

	@Test (expected = NullPointerException.class)
	public void nullContactsThrowsException() {
		mockMeeting = new MeetingMock(meetingID, meetingDate, null);
	}
	

	@Test (expected = IllegalArgumentException.class)
	public void negativeIDThrowsException() {
		mockMeeting = new MeetingMock(-1, meetingDate, meetingDelegates);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void zeroIDThrowsException() {
		mockMeeting = new MeetingMock(0, meetingDate, meetingDelegates);
	}

	@Test (expected = IllegalArgumentException.class)
	public void emptyContactsThrowsException() {
		Set<Contact> emptyContacts = new HashSet<>();
		mockMeeting = new MeetingMock(meetingID, meetingDate, emptyContacts);
	}

	private class MeetingMock implements Meeting {
	
		private int mockMeetingID;
		private Calendar mockMeetingDate;
		private Set<Contact> mockMeetingDelegates;
	
		private MeetingMock (int id, Calendar date, Set<Contact> contacts) {
			if (id <=0 ) {
				throw new IllegalArgumentException ("meeting ID must be positive and non-zero");
			}

			if (contacts.isEmpty()){
				throw new IllegalArgumentException ("Contacts cannot be empty");
			}			

			Objects.requireNonNull(date);
			Objects.requireNonNull(contacts);
			
			this.mockMeetingID = id;
			this.mockMeetingDate = date;
			this.mockMeetingDelegates = contacts;
		}
		@Override
		public int getId() {
			return mockMeetingID;
		}

		@Override
		public Calendar getDate() {
			return mockMeetingDate;
		}

		@Override
		public Set<Contact> getContacts() {
			return mockMeetingDelegates;
		}
	}

	@After
	public void tearDown() {
		contact1 = null;
		contact2 = null;
		contact3 = null;
		meetingID = 0;
		meetingDate = null;		
		meetingDelegates = null;

	}
}
