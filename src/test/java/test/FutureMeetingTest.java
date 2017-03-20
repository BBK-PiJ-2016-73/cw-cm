package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spec.FutureMeeting;
import spec.Meeting;
import impl.ContactImpl;
import impl.FutureMeetingImpl;
import spec.Contact;


public class FutureMeetingTest {
		
	private int meetingID;
	private Contact contact1, contact2, contact3;
	private Calendar meetingDate;
	private Set<Contact> meetingDelegates;

	@Before
	public void setUp() {
			
		meetingID = 45;
		meetingDate = new GregorianCalendar(2017,4,15);
			
		contact1 = new ContactImpl(448, "Exam Preparation Week", "It's all about studying");
		contact2 = new ContactImpl(449, "Read Exam key Reference", "Study all subjects");
		contact3 = new ContactImpl(450, "Mixed studies with work", "Manage time for exams");
			
		meetingDelegates = new HashSet<>();
		meetingDelegates.add(contact1);
		meetingDelegates.add(contact2);
		meetingDelegates.add(contact3);
		}

		@Test
		public void createFutureMeeting() {
			FutureMeeting futureMeeting = new FutureMeetingImpl(meetingID, meetingDate, meetingDelegates);
			assertNotNull(futureMeeting);
		}
		
		@Test
		public void createFutureMeetingofTypeMeeting() {
			Meeting futureMeeting = new FutureMeetingImpl(meetingID, meetingDate, meetingDelegates);
			assertNotNull(futureMeeting);
		}
		
		@Test 
		public void testFutureMeetingID() {
			FutureMeeting futureMeeting = new FutureMeetingImpl(meetingID, meetingDate, meetingDelegates);
			assertEquals(meetingID, futureMeeting.getId());
		}
		
		@Test
		public void testFutureMeetingDate() {
			FutureMeeting futureMeeting = new FutureMeetingImpl(meetingID, meetingDate, meetingDelegates);
			assertEquals(meetingDate, futureMeeting.getDate());
		}
		
		@Test
		public void testFutureMeetingDelegates() {
			FutureMeeting futureMeeting = new FutureMeetingImpl(meetingID, meetingDate, meetingDelegates);
			assertEquals(meetingDelegates, futureMeeting.getContacts());
		}
		
		@Test
		public void testEqualsFutureMeeting() {
			int meetingId2 = 45;
			Calendar meetingDate2 = new GregorianCalendar(2016,4,15);
			Set<Contact> meetingDelegates2 = new HashSet<>();
			meetingDelegates2.add(contact1);
			meetingDelegates2.add(contact2);
			meetingDelegates2.add(contact3);
			
			FutureMeeting futureMeeting2 = new FutureMeetingImpl(meetingId2, meetingDate2, meetingDelegates2);
			assertTrue(futureMeeting2.getId()==(meetingId2));
			assertTrue(futureMeeting2.getDate().equals(meetingDate2));
			assertTrue(futureMeeting2.getContacts().equals(meetingDelegates2));
		}
		
		
		@Test (expected = NullPointerException.class)
		public void nullDateThrowsException() {
			new FutureMeetingImpl(meetingID, null, meetingDelegates);
		}
		
		@Test (expected = NullPointerException.class)
		public void nullContactsThrowsException() {
			new FutureMeetingImpl(meetingID, meetingDate, null);
		}
		
		@Test (expected = NullPointerException.class)
		public void nullDateAndContactsThrowsException() {
			new FutureMeetingImpl(meetingID, null, meetingDelegates);
		}
		
		@Test (expected = IllegalArgumentException.class) 
		public void negativeIdThrowsException() {
			new FutureMeetingImpl(-1, meetingDate, meetingDelegates);
		}
		
		@Test (expected = IllegalArgumentException.class) 
		public void zeroIdThrowsException() {
			new FutureMeetingImpl(0, meetingDate, meetingDelegates);
		}
		
		@Test (expected = IllegalArgumentException.class) 
		public void emptyContactsThrowsException() {
			Set<Contact> emptyContacts = new HashSet<>();
			new FutureMeetingImpl(meetingID, meetingDate, emptyContacts);
		}
		
		
		@After
		public void tearDown() {
			meetingID = 0;
			meetingDate = null;	
			contact1 = null;
			contact2 = null;
			contact3 = null;
			meetingDelegates = null;
		}
		
	}