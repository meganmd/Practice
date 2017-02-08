package etsy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class MusicLibrary {

	private HashMap<String, Artist> artists = new HashMap<>();
	private TreeMap<Integer, HashSet<Track>> tracksByPlays = new TreeMap<>(Collections.reverseOrder());
	private TreeMap<Integer, HashSet<Artist>> artistsByPlays = new TreeMap<>(Collections.reverseOrder());
	
	public String addArtist(String name) throws MusicLibraryException {
		if(!artists.containsKey(name)) {
			Artist artist = new Artist(name);
			artists.put(name, artist);
			if(!artistsByPlays.containsKey(0)) {
				artistsByPlays.put(0, new HashSet<>());
			}
			artistsByPlays.get(0).add(artist);
			return "Artist " + artist.getName() + " added";
		} else {
			throw new MusicLibraryException(
					String.format("%s is already registered as an artist", 
					name.matches(".*\\s.*") ? "\"" + name + "\"" : name));
		}
	}
	
	public String addAlbum(String artistName, String albumName) throws MusicLibraryException {
		if(artists.containsKey(artistName)) {
			Artist artist = artists.get(artistName);
			Album album = artist.addAlbum(albumName);
			return album.getTitle() + " by " + artist.getName() + " added";
		} else {
			throw new MusicLibraryException(
					String.format("%s is already registered as an artist", 
					artistName.matches(".*\\s.*") ? "\"" + artistName + "\"" : artistName));
		}
	}
	
	public String addTrack(String artistName, String albumName, String trackName) throws MusicLibraryException {
		if(artists.containsKey(artistName)) {
			Artist artist = artists.get(artistName);
			Album album = artist.getAlbum(albumName);
			Track track = album.addTrack(trackName);
			if(!tracksByPlays.containsKey(0)) {
				tracksByPlays.put(0, new HashSet<>());
			}
			tracksByPlays.get(0).add(track);
			return track.getTitle() + " on " + album.getTitle() + " by " + artist.getName() + " added";
		} else {
			throw new MusicLibraryException(
					String.format("%s is not registered as an artist", 
					artistName.matches(".*\\s.*") ? "\"" + artistName + "\"" : artistName));
		}
	}
	
	public String listAlbums(String artistName) throws MusicLibraryException {
		if(artists.containsKey(artistName)) {
			Artist artist = artists.get(artistName);
			StringBuilder str = new StringBuilder();
			str.append("Albums by " + artist.getName() + ":");
			if (artist.hasNoAlbums()) {
				str.append(String.format("%sNo albums to list", System.lineSeparator()));
			}
			for(Album album : artist.getAlbums()) {
				str.append(String.format("%s  %s", System.lineSeparator(), album.getTitle()));
			}
			return str.toString();
		} else {
			throw new MusicLibraryException(
					String.format("%s is not registered as an artist", 
					artistName.matches(".*\\s.*") ? "\"" + artistName + "\"" : artistName));
		}
	}
	
	public String listTracks(String artistName, String albumName) throws MusicLibraryException {
		if(artists.containsKey(artistName)) {
			Artist artist = artists.get(artistName);
			Album album = artist.getAlbum(albumName);
			StringBuilder str = new StringBuilder();
			str.append("Tracks on " + album.getTitle() + " by " + artist.getName() + ":");
			if(album.hasNoTracks()) {
				str.append(String.format("%sNo tracks to list", System.lineSeparator()));
			}
			for(Track track : album.getTracks()) {
				str.append(String.format("%s  %s: %s", System.lineSeparator(), track.getTitle(), track.getPlayCount()));
			}
			return str.toString();
		} else {
			throw new MusicLibraryException(
					String.format("%s is not registered as an artist", 
					artistName.matches(".*\\s.*") ? "\"" + artistName + "\"" : artistName));
		}
	}
	
	public String listen(String artistName, String albumName, String trackName) throws MusicLibraryException {
		if(artists.containsKey(artistName)) {
			Artist artist = artists.get(artistName);
			Album album = artist.getAlbum(albumName);
			Track track = album.getTrack(trackName);
			track.play();
			
			artistsByPlays.get(artist.getPlayCount()-1).remove(artist);
			if(artistsByPlays.get(artist.getPlayCount()-1).isEmpty()) {
				artistsByPlays.remove(artist.getPlayCount()-1);
			}
			if(!artistsByPlays.containsKey(artist.getPlayCount())) {
				artistsByPlays.put(artist.getPlayCount(), new HashSet<>());
			}
			artistsByPlays.get(artist.getPlayCount()).add(artist);
			
			tracksByPlays.get(track.getPlayCount()-1).remove(track);
			if(tracksByPlays.get(track.getPlayCount()-1).isEmpty()) {
				tracksByPlays.remove(track.getPlayCount()-1);
			}
			if(!tracksByPlays.containsKey(track.getPlayCount())) {
				tracksByPlays.put(track.getPlayCount(), new HashSet<>());
			}
			tracksByPlays.get(track.getPlayCount()).add(track);
			
			
			return "Listened to: " + track.getTitle() + " on " + album.getTitle() + " by " + artist.getName();
		} else {
			throw new MusicLibraryException(
					String.format("%s is not registered as an artist", 
					artistName.matches(".*\\s.*") ? "\"" + artistName + "\"" : artistName));
		} 
	}
	
	public String listTopTracks(String N) {
		int n = Integer.parseInt(N);
		StringBuilder str = new StringBuilder();
		str.append(String.format("Top %s tracks:%s", N, System.lineSeparator()));
		if(tracksByPlays.isEmpty()) {
			str.append("No tracks to show");
		}
		for(Map.Entry<Integer, HashSet<Track>> entry : tracksByPlays.entrySet()) {
			if(n==0) break;
			for(Track track : entry.getValue()) {
				if(n == 0) break;
				str.append(String.format("  %s on %s by %s: %s%s", 
						track.getTitle(), track.album.getTitle(), track.album.artist.getName(), 
						track.getPlayCount(), System.lineSeparator()));
				n--;
			}
		}
		return str.toString();
	}
	
	public String listTopArtists(String N) {
		int n = Integer.parseInt(N);
		StringBuilder str = new StringBuilder();
		str.append(String.format("Top %s artists:%s", N, System.lineSeparator()));
		if(artistsByPlays.isEmpty()) {
			str.append("No artists to show");
		}
		for(Map.Entry<Integer, HashSet<Artist>> entry : artistsByPlays.entrySet()) {
			if(n==0) break;
			for(Artist artist : entry.getValue()) {
				if(n == 0) break;
				str.append(String.format("  %s: %s%s", artist.getName(), artist.getPlayCount(), System.lineSeparator()));
				n--;
			}
		}
		return str.toString();
	}
	
	public String listLibraryContents() {
		StringBuilder str = new StringBuilder();
		for (Artist artist : artists.values()) {
			str.append(String.format("%s%s", artist.getName(), System.lineSeparator()));
			for(Album album : artist.getAlbums()) {
				str.append(String.format("     %s played: %s%s", album.getTitle(), album.getPlayCount(), System.lineSeparator()));
				for(Track track : album.getTracks()) {
					str.append(String.format("        %s played: %s%s", track.getTitle(), track.getPlayCount(), System.lineSeparator()));
				}
			}
		}
		return str.toString();
	}	
	
	class Artist {
		private String name;
		private HashMap<String, Album> albums = new HashMap<>();
		private int playCount = 0;
		public Artist(String name) {
			this.name = name;
		}
		public boolean hasNoAlbums() {
			return albums.isEmpty();
		}
		public Collection<Album> getAlbums() {
			return  albums.values();
		}
		public int getPlayCount() {
			return playCount;
		}
		public Album getAlbum(String title) throws MusicLibraryException {
			if (albums.containsKey(title)) {
				return albums.get(title);
			} else {
				throw new MusicLibraryException(
						String.format("%s does not have an album titled %s.", 
						this.getName(),
						title.matches(".*\\s.*") ? "\"" + title + "\"" : title));
			}
		}
		public Album addAlbum(String title) throws MusicLibraryException {
			if(!albums.containsKey(title)) {
				Album album = new Album(this, title);
				albums.put(title, album);
				return album;
			} else {
				throw new MusicLibraryException(
						String.format("The album titled %s by %s already exists",
								title.matches(".*\\s.*") ? "\"" + title + "\"" : title,
								this.getName()));
			}
		}
		public void play() {
			playCount++;
		}
		public String getName() {
			return name.matches(".*\\s.*") ? "\"" + name + "\"" : name;
		}
	}
	
	class Album {
		private String title;
		public Artist artist;
		private int playCount = 0;
		private HashMap<String, Track> tracks = new HashMap<>();

		public Album(Artist artist, String title) {
			this.artist = artist;
			this.title = title;
		}
		public boolean hasNoTracks() {
			return tracks.isEmpty();
		}
		public Track getTrack(String trackName) throws MusicLibraryException {
			if(tracks.containsKey(trackName)) {
				return tracks.get(trackName);
			} else {
				throw new MusicLibraryException(
						String.format("The album %s by %s does not have a track titled %s",
								this.getTitle(),
								artist.getName(),
								trackName.matches(".*\\s.*") ? "\"" + trackName + "\"" : trackName));
			}
		}
		public Collection<Track> getTracks() {
			return  tracks.values();
		}
		public Track addTrack(String trackName) throws MusicLibraryException {
			if(!tracks.containsKey(trackName)) {
				Track track = new Track(this, trackName);
				tracks.put(trackName, track);
				return track;
			} else {
				throw new MusicLibraryException(
						String.format("The album %s by %s already has a track titled %s",
								this.getTitle(),
								artist.getName(),
								trackName.matches(".*\\s.*") ? "\"" + trackName + "\"" : trackName));			}
		}
		public void play() {
			playCount++;
			artist.play();
		}
		public int getPlayCount() {
			return playCount;
		}
		public String getTitle() {
			return title.matches(".*\\s.*") ? "\"" + title + "\"" : title;
		}
	}
	
	class Track {
		private String title;
		public Album album;
		private int playCount = 0;
		
		public Track(Album album, String trackName) {
			this.album = album;
			this.title = trackName;
		}
		public int getPlayCount() {
			return playCount;
		}
		public void play() {
			playCount++;
			album.play();
		}
		public String getTitle() {
			return title.matches(".*\\s.*") ? "\"" + title + "\"" : title;
		}
	}
	
	class MusicLibraryException extends Exception {

		private static final long serialVersionUID = 1L;

		public MusicLibraryException(String string) {
			super(string);
		}
		
	}

}
