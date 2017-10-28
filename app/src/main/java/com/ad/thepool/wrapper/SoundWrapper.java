package com.ad.thepool.wrapper;

import java.io.Serializable;

import android.app.Activity;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.ad.thepool.Game;
import com.ad.thepool.PoolThread;
import com.ad.thepool.R;

public class SoundWrapper implements Serializable 
{
	private static final long serialVersionUID = 4881029806428404588L;

	private int soundID;
	private static SoundPool spool;
	private MediaPlayer player;
	private static Activity activity;
	private Game game;
	private boolean isMusic;
	
	static 
	{
		spool = new SoundPool(30,AudioManager.STREAM_MUSIC,0);
		
		
	}
	
	
	public SoundWrapper(Game game, String file, boolean isMusic) 
	{
		this.game = game;
		this.isMusic = isMusic;
		loadSong(file);
	}
	
	public void restoreState()
	{
	}
	
	private void loadSong(String songPath)
	{
		Resources res = PoolThread.mContext.getResources();

	//	soundID = spool.load(songPath + extension, 1);
		if(isMusic == false)
		{
			soundID = spool.load(PoolThread.mContext, res.getIdentifier("com.ad.thepool:raw/" + songPath,null,null), 1);
		}
		else
		{
			player = MediaPlayer.create(PoolThread.mContext, res.getIdentifier("com.ad.thepool:raw/" + songPath,null,null));
		}
				
	}
	
	
	public void loop() 
	{
		if((isMusic == true && game.isMusic()) || (isMusic == false && game.isSound()))
		{
			if(isMusic == false)
			{
				AudioManager sysAudioManager = (AudioManager) activity.getSystemService(Activity.AUDIO_SERVICE);
				
				float actualVolume = (float) sysAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				float maxVolume = (float) sysAudioManager
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = actualVolume / maxVolume;
				spool.setLoop(soundID, 10);
				spool.play(soundID, volume, volume, 1, 0, 1f);
			}
			else
			{
				player.setVolume(0.5F, 0.5F);
				player.setLooping(true);
				player.start();
			}
		}
	}
	
	public void play() 
	{
		if((isMusic == true && game.isMusic()) || (isMusic == false && game.isSound()))
		{
			if(isMusic == false)
			{
				AudioManager sysAudioManager = (AudioManager) activity.getSystemService(Activity.AUDIO_SERVICE);
				
				float actualVolume = (float) sysAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				float maxVolume = (float) sysAudioManager
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = actualVolume / maxVolume;
				spool.setLoop(soundID, 0);
				spool.play(soundID, volume, volume, 1, 0, 1f);
			}
			else
			{
				player.setVolume(0.5F, 0.5F);
				player.setLooping(false);
				player.start();
			}
		}
	}

	public void stop() 
	{
		if(isMusic == false)
		{
			spool.stop(soundID);
		}
		else
		{
			player.stop();
		}
	}
	
	public void pause() 
	{
		if(isMusic == false)
		{
			spool.pause(soundID);
		}
		else
		{
			player.pause();
		}
	}
	
	public boolean isMusic() {
		return isMusic;
	}
	
	public static void setActivity(Activity activity) {
		SoundWrapper.activity = activity;
	}

}
