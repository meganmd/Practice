package etsy;

import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etsy.MusicLibrary.Artist;
import etsy.MusicLibrary.MusicLibraryException;

public class MusicLibraryTest {
	MusicLibrary library;
	
	/*public static void main(String[] args) {
		MusicLibraryTest test = new MusicLibraryTest();
		test.setUp();
		test.testListen();
		test.tearDown();
		
		test.testAddAlbumFailExists();
		test.testAddAlbumFailNoArtist();
		test.testAddArtistFail();
		test.testAddTrackFailAlreadyExists();
		test.testAddTrackFailNoAlbum();
		test.testAddTrackFailNoArtist();
		test.testListenFailNoAlbum();
		test.testListenFailNoAlbum();
		test.testListenFailNoArtist();
		test.testListenFailNoTrack();
		
		test.tearDown();
	}*/

	@Before
	public void setUp() {
		library = new MusicLibrary();
	}
	
	@After
	public void tearDown() {
		library.listLibraryContents();
	}

	@Test
	public void testAddArtist() {
		try {
			assertTrue(library.addArtist("billy").equals("Artist billy added\n"));
			assertTrue(library.addArtist("\"bob\"").equals("Artist bob added\n"));
			assertTrue(library.addArtist("silly sally").equals("Artist \"silly sally\" added\n"));
			assertTrue(library.addArtist("\"sally silly\"").equals("Artist \"sally silly\" added\n"));
			
			assertTrue(library.addArtist("billy").equals("billy is already registered as an artist\n"));
			
			assertTrue(library.addAlbum("billy", "goat").equals("goat by billy added\n"));
			assertTrue(library.addAlbum("billy", "I love food").equals("\"I love food\" by billy added\n"));
			assertTrue(library.addAlbum("silly sally", "I love food").equals("\"I love food\" by \"silly sally\" added\n"));
			
		} catch (MusicLibraryException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
		
	@Test 
	public void testAddArtistFail() {
		testAddArtist();
		try {
			System.out.println(library.addArtist("billy"));
			assertTrue(false);
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			assertTrue(e.getMessage().equals("billy is already registered as an artist"));
		}	// already exists
	}
		
	@Test
	public void testAddAlbum() {
		testAddArtist();
		try {
			System.out.println(library.addAlbum("billy", "goat"));
			System.out.println(library.addAlbum("billy", "cow"));
			System.out.println(library.addAlbum("billy", "sheep"));
			System.out.println(library.addAlbum("bob" , "sponge"));
			System.out.println(library.addAlbum("bob", "ocean"));
			System.out.println(library.addAlbum("bob", "sea"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddAlbumFailExists() {	
		testAddAlbum();
		try {
			System.out.println(library.addAlbum("billy", "goat")); //already exists
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddAlbumFailNoArtist() {
		testAddAlbum();
		try {
			System.out.println(library.addAlbum("sally", "shell")); //no such artist
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddTrack() {
		testAddAlbum();
		try {
			System.out.println(library.addTrack("billy", "goat", "tinCan"));
			System.out.println(library.addTrack("billy", "goat", "tinFoil"));
			System.out.println(library.addTrack("billy", "goat", "applesauce"));
			System.out.println(library.addTrack("billy", "cow", "moo"));
			System.out.println(library.addTrack("billy", "cow", "mooo"));
			System.out.println(library.addTrack("billy", "cow", "moooo"));
			System.out.println(library.addTrack("billy", "sheep", "baa"));
			System.out.println(library.addTrack("bob", "sponge", "underneath"));
			System.out.println(library.addTrack("bob", "sponge", "pineapple"));
			System.out.println(library.addTrack("bob", "ocean", "waves"));
			System.out.println(library.addTrack("bob", "ocean", "whales"));
			System.out.println(library.addTrack("bob", "sea", "salt"));
			System.out.println(library.addTrack("bob", "sea", "weed"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddTrackFailAlreadyExists() {
		testAddTrack();
		try {
			System.out.println(library.addTrack("bob", "sponge", "pineapple")); //already exists
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddTrackFailNoArtist() {
		testAddTrack();
		try {
			System.out.println(library.addTrack("sally", "sponge", "pineapple")); //no such artist
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddTrackFailNoAlbum() {
		testAddTrack();
		try {
			System.out.println(library.addTrack("bob", "spongier", "pineapple")); //no such album
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListen() {
		testAddTrack();
		try {
			System.out.println(library.listen("bob", "sponge", "pineapple"));
			System.out.println(library.listen("bob", "sponge", "pineapple"));
			System.out.println(library.listen("bob", "sponge", "pineapple"));
			System.out.println(library.listen("bob", "sponge", "underneath"));
			System.out.println(library.listen("billy", "goat", "tinCan"));
			System.out.println(library.listen("billy", "goat", "tinCan"));
			System.out.println(library.listen("billy", "goat", "tinCan"));
			System.out.println(library.listen("billy", "goat", "tinCan"));
			System.out.println(library.listen("billy", "goat", "tinCan"));
			System.out.println(library.listen("billy", "goat", "tinFoil"));
			System.out.println(library.listen("billy", "goat", "applesauce"));
			System.out.println(library.listen("billy", "cow", "moo"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListenFailNoTrack() {
		testAddTrack();
		try {
			System.out.println(library.listen("billy", "cow", "baa"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListenFailNoAlbum() {
		testAddTrack();
		try {
			System.out.println(library.listen("billy", "bull", "baa"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListenFailNoArtist() {
		testAddTrack();
		try {
			System.out.println(library.listen("sally", "cow", "baa"));
		} catch (MusicLibraryException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
