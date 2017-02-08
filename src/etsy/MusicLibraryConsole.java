package etsy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import etsy.MusicLibrary.MusicLibraryException;

public class MusicLibraryConsole {
	MusicLibrary library = new MusicLibrary();
	
	public static void main(String[] args) {
		System.out.println("Starting Music Library...");
		MusicLibraryConsole console = new MusicLibraryConsole();
		console.run();
	}
	
	private void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input: ");
		String input = sc.nextLine();
		while(!input.equals("quit")) {
			if(input.equals("build sample")) {
				buildSample();
			} else {
				try {
					System.out.println(execute(input));
				} catch (Exception e) {
					library.listLibraryContents();
					sc.close();
					throw e;
				}
			}
			System.out.println("input: ");
			input = sc.nextLine();
		}
		sc.close();
		System.out.println("Quitting...");
	}
	
	String execute(String input) {
		try {
			if(Pattern.matches("^add artist (\\S+|\".+\")$", input)) {
				Pattern pattern = Pattern.compile("^add artist ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String artistName = matcher.group(2)!= null ? matcher.group(2) : matcher.group(3);
	
				return library.addArtist(artistName);
			} else if(input.matches("^add album ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$")) {
				Pattern pattern = Pattern.compile("^add album ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String albumName = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
				String artistName = matcher.group(5) != null ? matcher.group(5) : matcher.group(6);
				return library.addAlbum(artistName, albumName);
			} else if(input.matches("^add track ((\\S+)|\"(.+)\") on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$")) {
				Pattern pattern = Pattern.compile("^add track ((\\S+)|\"(.+)\") on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String trackName = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
				String albumName = matcher.group(5) != null ? matcher.group(5) : matcher.group(6);
				String artistName = matcher.group(8) != null ? matcher.group(8) : matcher.group(9);
				return library.addTrack(artistName, albumName, trackName);
			} else if(input.matches("^list albums by ((\\S+)|\"(.+)\")$")) {
				Pattern pattern = Pattern.compile("^list albums by ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String artistName = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
				return library.listAlbums(artistName);
			} else if(input.matches("^list tracks on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$")) {
				Pattern pattern = Pattern.compile("^list tracks on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String albumName = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
				String artistName = matcher.group(5) != null ? matcher.group(5) : matcher.group(6);
				return library.listTracks(artistName, albumName);
			} else if(input.matches("^listen to ((\\S+)|\"(.+)\") on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$")) {
				Pattern pattern = Pattern.compile("^listen to ((\\S+)|\"(.+)\") on ((\\S+)|\"(.+)\") by ((\\S+)|\"(.+)\")$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String trackName = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
				String albumName = matcher.group(5) != null ? matcher.group(5) : matcher.group(6);
				String artistName = matcher.group(8) != null ? matcher.group(8) : matcher.group(9);
				return library.listen(artistName, albumName, trackName);
			} else if(input.matches("^list top (\\d+) tracks$")) {
				Pattern pattern = Pattern.compile("^list top (\\d+) tracks$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String N = matcher.group(1);
				return library.listTopTracks(N);
			} else if(input.matches("^list top (\\d+) artists$")) {
				Pattern pattern = Pattern.compile("^list top (\\d+) artists$");
				Matcher matcher = pattern.matcher(input);
				matcher.find();
				String N = matcher.group(1);
				return library.listTopArtists(N);
			} else if(input.matches("show all")) {
				return library.listLibraryContents();
			}
			return "improperly formatted input";
		} catch (MusicLibraryException e) {
			return e.getMessage();
		}
	}

	private String buildSample() {
		try {
			File file = new File("resources/sample_in.txt");
			Scanner sc = new Scanner(file);
			String input = sc.nextLine();
			while(sc.hasNextLine()) {
				System.out.println(execute(input));
				input = sc.nextLine();
			}
			sc.close();
			return "Built sample";	
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return "No sample found";
		}
	}
}
