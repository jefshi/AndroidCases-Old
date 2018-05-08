package com.project.day1008.player.provider;

public class Music {
	private int head;
	private String title;
	private String artist;
	private String path;
	
	public Music(String title, String artist) {
		super();
		this.title = title;
		this.artist = artist;
		head = 0;
		path = null;
	}
	
	public Music(String title, String artist, int head) {
		super();
		this.title = title;
		this.artist = artist;
		this.head = head;
		path = null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return "Music [title=" + title + ", artist=" + artist + ", head=" + head + ", path=" + path + "]";
	}

}
