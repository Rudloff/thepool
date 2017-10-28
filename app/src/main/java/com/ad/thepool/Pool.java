package com.ad.thepool;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.ad.thepool.wrapper.SoundWrapper;
import com.ad.thepool.wrapper.StreamWrapper;

public class Pool extends Activity {
	/** A handle to the View in which the game is running. */
	private PoolView poolView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		poolView = (PoolView) findViewById(R.id.pool);

		poolView.setSaveState(savedInstanceState);

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		SoundWrapper.setActivity(this);
		StreamWrapper.setActivity(this);

	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		poolView.pause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);

		poolView.saveState(outState);

		Log.w(this.getClass().getName(), "SIS called");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		poolView.doDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getName(), "Backkey");
		poolView.stopMusic();
		super.onBackPressed();
	}

}