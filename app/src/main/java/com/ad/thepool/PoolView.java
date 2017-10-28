package com.ad.thepool;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class PoolView extends SurfaceView implements SurfaceHolder.Callback {
	private PoolThread thread;
	private int viewx = 0, viewy = 0;
	private Context context;
	private SurfaceHolder holder;
	private Bundle saveState;

	public PoolView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setKeepScreenOn(true);

		holder = getHolder();
		this.context = context;

		 Display display = ((WindowManager)
		 context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		 viewx = display.getWidth();
		 viewy = display.getHeight();

		 Log.d("pool","viewConst" + viewx + " " + viewy );
		 
		 // register our interest in hearing about changes to our surface
		// SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		requestFocus();

		// create thread only; it's started in surfaceCreated()
		// create thread only; it's started in surfaceCreated()
		// thread = new PoolThread(holder, context, new Handler() {
		// @Override
		// public void handleMessage(Message m) {
		// }
		// }, width, height);

		setFocusable(true); // make sure we get key events

	}
	
	public void stopMusic()
	{
		thread.stopMusic();
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d("pool", "sMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d("pool", "sMeasureOld" + viewx + " " + viewy);

//		if (viewx != 0)
//			this.setMeasuredDimension(viewx, viewy);
//		else {
//			int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//			int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
//
			int mviewx = Game.calcViewX(viewx, viewy);
			int mviewy = Game.calcViewY(viewx, viewy);
//			Log.d("pool", "sMeasure" + viewx + " " + viewy);

			this.setMeasuredDimension(mviewx, mviewy);
//		}
	}

	/**
	 * Standard override to get key-press events.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {
		if (thread != null) {

		return thread.doKeyDown(keyCode, msg);
		}
		return false;
	}

	/**
	 * Standard override for key-up. We actually care about these, so we can
	 * turn off the engine or stop rotating.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) {
		if (thread != null) {
		return thread.doKeyUp(keyCode, msg);
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (thread != null) {
			return thread.onTouchEvent(event);
		}
		return false;
	}

	/**
	 * Standard window-focus override. Notice focus lost so we can pause on
	 * focus lost. e.g. user switches to take a call.
	 */
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus) {
			Log.d("pool", "sFocus+Pause");

			if (thread != null) {
				thread.pause();
			}
		}
	}

	/* Callback invoked when the surface dimensions change. */
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d("pool", "sChanged" + width + height);
		// setLayoutParams(new LayoutParams(width, height));
		// thread.setSurfaceSize(width, height);
	}

	/*
	 * Callback invoked when the Surface has been created and is ready to be
	 * used.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		Log.d("pool", "sCreated");
		thread = new PoolThread(holder, context, new Handler());
		thread.doStart();
		if(saveState == null)
		{
			thread.init(viewx, viewy);
		}
		else
		{
			thread.restoreState(saveState);
		}
		// setLayoutParams(new LayoutParams(thread.getGraphicsSizeX(),
		// thread.getGraphicsSizeY()));
		thread.setRunning(true);
		thread.start();

	}

	/*
	 * Callback invoked when the Surface has been destroyed and must no longer
	 * be touched. WARNING: after this method returns, the Surface/Canvas must
	 * never be touched again!
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		Log.d("pool", "sDestroyed");

		if(thread == null)
			return;
		
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void pause()
	{
		Log.d("pool", "pause");

		if(thread != null)
			thread.pause();
		
	}
	
	public void saveState(Bundle outState)
	{
		Log.d("pool", "save");

		if(thread != null)
			thread.saveState(outState);
	}

	public PoolThread getThread() {
		return thread;
	}

	public void setSaveState(Bundle saveState) {
		this.saveState = saveState;
	}
	
	protected void doDestroy() 
	{
		if(thread != null)
		{
			thread.doDestroy();
		}
	}
	
}
