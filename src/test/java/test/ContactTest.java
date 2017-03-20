package test;

import impl.ContactImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spec.Contact;

public class ContactTest {
	private int testiId;
	private Contact testContact1, testContact2, testContact3;
    private String testNotes1, testNotes2 , testNotes3;
	private String testName1, testName2, testName3;
 
	
    @Before
    public void setUp() {
        testiId = 47;
        testName1 = "Billy Joe-White";
        testName2 = "Penny Farthing Jones";
		testName3 = "Donald Trap";
        testNotes1 = "Its all about getting past the first year";
        testNotes2 = "Exams exams exams";
		testNotes3 = "Reading Reading";

        testContact1 = new ContactImpl(testiId, testName1, testNotes1);
        testContact2 = new ContactImpl(testiId, testName2, testNotes2);
		testContact3 = new ContactImpl(testiId, testName3, testNotes3);
    }
    

    @Test
    public void testiIdfromConstructor() {
    	assertEquals(testiId,testContact1.getId());
    }
	
    @Test
    public void testNamefromConstructor() {
    	assertEquals(testName1, testContact1.getName());
    }
	
    @Test
    public void testNotesFromConstructor() {
    	assertEquals("\n" + testNotes1, testContact1.getNotes());
    }
	
    @Test (expected = NullPointerException.class)
    public void testConstructorNullName() {
    	new ContactImpl(testiId, null, testNotes1);
    }
	
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorNegativeID() {
    	new ContactImpl(-1, testName2, testNotes2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorZeroID() {
    	new ContactImpl(0, testName2, testNotes2);
    }
	
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorEmptyName() {
    	new ContactImpl(testiId, "", testNotes1);
    }
    

    @Test (expected = NullPointerException.class)
    public void testConstructorNullNotes() {
    	new ContactImpl(testiId, testName1, null);
    }
	
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorEmptyNotes() {
    	new ContactImpl(testiId, testName1,"");
    }
    

    @Test (expected = NullPointerException.class)
    public void testConstructorNotesWithNullName(){
    	new ContactImpl(testiId, null,testNotes1);
    }
	
	@Test 
	public void testContactGetIdReturnsInt() {
		assertTrue (testContact1.getId() == (int)testContact1.getId());
	}
	
	@Test 
	public void testContactGetIdReturnsID() {
		assertEquals (testiId, testContact1.getId());
	}
	
	@Test
	public void testContactGetNameReturnsExpectedName() {
		assertEquals (testName1, testContact1.getName());
	}
	
	@Test
	public void testContactGetNameWithSpaces() {
		assertEquals (testName2, testContact2.getName());
	}

	@Test
	public void testContactGetNotesReturnsNotes() {
		assertEquals ("\n" + testNotes2, testContact2.getNotes());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddNotesToEmptyNote() {
		Contact testCt4 = new ContactImpl(testiId, testName1, "");
		testCt4.addNotes(testNotes2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddNotesToNullNote() {
		Contact testCt5 = new ContactImpl(testiId, testName1, null);
		testCt5.addNotes(testNotes2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyNotesToNote() {
		Contact testCt5 = new ContactImpl(testiId, testName1, testNotes1);
		testCt5.addNotes("");
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddNullNotes() {
		Contact testCt6 = new ContactImpl(testiId, testName1, testNotes2);
		testCt6.addNotes(null);
	}
	
	
	@After
    public void tearDown() {
		testiId = 0;
		testContact1 = null;
		testContact2 = null;
        testName1 = null;
        testName2 = null;
        testNotes1 = null;
        testNotes2 = null;
       
    }
	
}