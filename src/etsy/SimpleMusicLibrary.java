package etsy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class SimpleMusicLibrary {
	private HashMap<String, HashMap<String, HashSet<String>>> artists = new HashMap<>();
	private TreeMap<Integer, HashSet<String>> artistsByPlays = new TreeMap<>();
	private TreeMap<Integer, HashSet<String>> tracksByPlays = new TreeMap<>();
	
	public void addArtist(String artistName) {
		if(!artists.containsKey(artistName)) {
			artists.put(artistName, new HashMap<>());
		}
		if(!artistsByPlays.containsKey(0)) {
			artistsByPlays.put(0, new HashSet<>());
		}
		artistsByPlays.get(0).add(artistName);
		
	}
	
	public void addAlbum(String artistName, String albumName) {
		if(artists.containsKey(artistName)) {
			HashMap<String,HashSet<String>> artist = artists.get(artistName);
			if(!artist.containsKey(albumName)) {
				artist.put(albumName, new HashSet<>());
			}
		}
	}
	
	public void addTrack(String artistName, String albumName, String trackName) {
		if(artists.containsKey(artistName)) {
			HashMap<String,HashSet<String>> artist = artists.get(artistName);
			if(artist.containsKey(albumName)) {
				HashSet<String> album = artist.get(albumName);
				if(!album.contains(trackName)) {
					album.add(trackName);
				}
			}
		}
	}
	
	public void listAlbumsByArtist(String artistName) {
		if(artists.containsKey(artistName)) {
			HashMap<String, HashSet<String>> artist = artists.get(artistName);
			System.out.println("Albums by " + artistName);
			for(String albumTitle : artist.keySet()) {
				System.out.println(albumTitle);
			}
		}
	}
	
	public void listTracksOnAlbumByArtist(String artistName, String albumName) {
		if(artists.containsKey(artistName)) {
			HashMap<String, HashSet<String>> artist = artists.get(artistName);
			if(artist.containsKey(albumName)) {
				HashSet<String> album = artist.get(albumName);
				System.out.println("Tracks on " + albumName + " by " + artistName);
				for(String track : album) {
					System.out.println(track);
				}
			}
		}
	}
	
	public void listenTo(String artistName, String albumName, String trackName) {
		
	}
	
	public void listTopArtists(int n) {
		
	}
	
	public void listTopTracks(int n) {
		
	}

}
