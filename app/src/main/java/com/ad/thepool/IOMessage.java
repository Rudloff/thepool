package com.ad.thepool;

public class IOMessage 
{
	public static final int TYPE_LOAD = 0;
	public static final int TYPE_SAVE = 1;
	public static final int TYPE_DONE = 2;
	
	private int type;
	private Game game;
	private String filename;
	private String rfilename;
	
	public IOMessage(int type, Game game, String filename, String rfilename) {
		super();
		this.type = type;
		this.game = game;
		this.filename = filename;
		this.rfilename = rfilename;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRfilename() {
		return rfilename;
	}
	public void setRfilename(String rfilename) {
		this.rfilename = rfilename;
	}
	
	
}
