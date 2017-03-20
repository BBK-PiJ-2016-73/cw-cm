package test;
import spec.*;
import impl.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.*;


public class ContactManagerTest {
    
    private spec.ContactManager contactManager;
    private Set<spec.Contact>   contacts;
    private spec.Contact 	   contact;
    private Calendar 	   futureDate;
    private Calendar 	   pastDate;
    private final String   FILENAME = "contacts.txt";



    @Before
    public void setUp() {

        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        this.futureDate = futureDate;

        Calendar pastDate = Calendar.getInstance();
        pastDate.set(2018, 01, 01);
        this.pastDate   = pastDate;
        spec.ContactManager contactManager = new impl.ContactManagerImpl();

        int contactId = contactManager.addNewContact("Billy Jones-White", "Preparing for the exams");
        this.contactManager = contactManager;
        this.contacts = contactManager.getContacts("Billy Jones-White");
        Set<spec.Contact> contacts  = contactManager.getContacts(contactId);
        Object[] contactsArray = contacts.toArray();
        this.contact = (spec.Contact) contactsArray[0];

        try {

            Path p = FileSystems.getDefault().getPath(FILENAME);
            if (Files.exists(p)) {
                Files.delete(p);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }



    @Test(expected=NullPointerException.class)
    public void testAddFutureMeetingWithNullContacts() {
        this.contactManager.addFutureMeeting(null, this.futureDate);
    }
    
    @Test(expected=NullPointerException.class)
    public void testAddFutureMeetingWithNullDate() {

        this.contactManager.addFutureMeeting(this.contacts, null);
    }





    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithAEmptySetOfContacts() {

        Set<Contact> contacts = new HashSet<Contact>();
        this.contactManager.addFutureMeeting(contacts, this.futureDate);

    }


    @Test
    public void testCorrectReturnValueFromAddFutureMeetingWithValidInput() {

        int newKey = this.contactManager.addFutureMeeting(this.contacts, this.futureDate);
        assertEquals("The ID for the new future meeting was expected to be 1.", 1, newKey);
    }
    

}