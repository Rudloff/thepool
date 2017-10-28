package com.ad.thepool;

public interface ApplicationFrame {
	public Game getGame();

	public void setGame(Game game);

	public void setFrameGoal(int quality);
	
	public void saveGame(Game game, String filename, String rfilename);

	public void loadGame(String filename);
	
	public boolean isAndroid();

}
