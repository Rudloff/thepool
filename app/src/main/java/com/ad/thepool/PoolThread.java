package com.ad.thepool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;
import com.ad.thepool.wrapper.Log;

public class PoolThread extends Thread implements ApplicationFrame {

	long frameStartTime;
	long frameEndTime;
	long frameDiffTime = 0;
	long frameDiffSum = 0;
	long frames = 0;
	long frameAvg;
	int frameWait = 0;

	private static final int frameGoalMed = 30;
	private static final int frameGoalHigh = 5;
	private static final int frameGoalLow = 80;

	private int frameGoal;

	private boolean waitMessage;

	private static Handler mMainHandler, mChildHandler;

	/** Handle to the surface manager object we interact with */
	private SurfaceHolder mSurfaceHolder;

	private boolean mRun;
	/**  */
	public static Context mContext;

	private GraphicsWrapper graphicsWrapper;

	private Game game;

	private boolean mouseDown;
	private int lastMousex, lastMousey;

	public PoolThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		// get handles to some important objects
		Log.d("tCon");
		mSurfaceHolder = surfaceHolder;
		mContext = context;

		mMainHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				Log.d("message from client");
				IOMessage msgObj = (IOMessage) msg.obj;
				if (msgObj.getType() == IOMessage.TYPE_DONE) {
					waitMessage = false;
				}
			}
		};

		new ChildThread().start();
	}

	/**
	 * Starts the game, setting parameters for the current difficulty.
	 */
	public void doStart() {
		synchronized (mSurfaceHolder) {

			Log.d("tStart");
			game = new Game(this);
		}
	}

	public void init(int x, int y) {
		game.init(x, y);
	}
	
	public void stopMusic()
	{
		game.setMusic(false);
	}

	/**
	 * Pauses the physics update & animation.
	 */
	public void pause() {
		Log.d("tpause");

		synchronized (mSurfaceHolder) {
			if (game != null) {
				game.pause();
			}
		}
	}

	public void mainMenu() {
		Log.d("tpause");

		synchronized (mSurfaceHolder) {
			game.mainMenu();
		}
	}

	public void restart() {
		Log.d("tpause");

		synchronized (mSurfaceHolder) {
			game.restartScene();
		}
	}

	public void lastCheckpoint() {
		Log.d("tpause");

		synchronized (mSurfaceHolder) {
			game.lastCheckpoint();
		}
	}

	@Override
	public void run() {
		while (mRun) {
			Canvas c = null;
			try {
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					doDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	/**
	 * Dump game state to the provided Bundle. Typically called when the
	 * Activity is being suspended.
	 * 
	 * @return Bundle with this view's state
	 */
	public Bundle saveState(Bundle map) {
		synchronized (mSurfaceHolder) {
			if (map != null) {
				Log.d("save");
				try {
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					ObjectOutputStream oout = new ObjectOutputStream(bout);
					oout.writeObject(game);
					oout.close();
					map.putByteArray("serialized", bout.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// game.saveSerializedGame("save.xml");
			}
		}
		return map;
	}

	/**
	 * Restores game state from the indicated Bundle. Typically called when the
	 * Activity is being restored after having been previously destroyed.
	 * 
	 * @param savedState
	 *            Bundle containing the game state
	 */
	public synchronized void restoreState(Bundle savedState) {
		synchronized (mSurfaceHolder) {
			Log.d("restore");
			try {

				ByteArrayInputStream bin = new ByteArrayInputStream(savedState.getByteArray("serialized"));
				Game game = (Game) new ObjectInputStream(bin).readObject();
				game.setFrame(this);
				game.getFrame().setGame(game);
				this.game = game;
				this.game.restoreSerializedState();
				this.game.pause();
				// graphicsWrapper = new GraphicsWrapper(false);
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Used to signal the thread whether it should be running or not. Passing
	 * true allows the thread to run; passing false will shut it down if it's
	 * already running. Calling start() after this was most recently called with
	 * false will result in an immediate shutdown.
	 * 
	 * @param b
	 *            true to run, false to shut down
	 */
	public void setRunning(boolean b) {
		mRun = b;
	}

	/* Callback invoked when the surface dimensions change. */
	// public void setSurfaceSize(int width, int height) {
	// // synchronized to make sure these all change atomically
	// Log.d("pool","tsurface");
	//
	// synchronized (mSurfaceHolder) {
	// mCanvasWidth = width;
	// mCanvasHeight = height;
	// }
	// }

	/**
	 * Resumes from a pause.
	 */
	public void unpause() {
		// Move the real time clock up to now
		synchronized (mSurfaceHolder) {
		}
	}

	/**
	 * Handles a key-down event.
	 * 
	 * @param keyCode
	 *            the key that was pressed
	 * @param msg
	 *            the original event object
	 * @return true
	 */
	boolean doKeyDown(int keyCode, KeyEvent msg) {
		synchronized (mSurfaceHolder) {
			if (waitMessage == true)
				return true;
			game.keyDown(KeyEventWrapper.convertKey(keyCode));
		}
		return true;
	}

	/**
	 * Handles a key-up event.
	 * 
	 * @param keyCode
	 *            the key that was pressed
	 * @param msg
	 *            the original event object
	 * @return true if the key was handled and consumed, or else false
	 */
	boolean doKeyUp(int keyCode, KeyEvent msg) {
		synchronized (mSurfaceHolder) {
			if (waitMessage == true)
				return true;
			game.keyUp(KeyEventWrapper.convertKey(keyCode));
		}
		return true;
	}

	public boolean onTouchEvent(MotionEvent event) {
		synchronized (mSurfaceHolder) {
			if (waitMessage == true)
				return true;

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				Log.d("mdown");
				lastMousex = (int) event.getX();
				lastMousey = (int) event.getY();
				game.mouseDown(lastMousex, lastMousey);
				mouseDown = true;
				return true;
			} else if (action == MotionEvent.ACTION_MOVE) {
				Log.d("mmove");
				lastMousex = (int) event.getX();
				lastMousey = (int) event.getY();
				game.mouseMove(lastMousex, lastMousey);
				return true;
			} else if (action == MotionEvent.ACTION_UP) {
				Log.d("mup");
				game.mouseUp((int) event.getX(), (int) event.getY());
				mouseDown = false;
				return true;
			}
		}
		return false;

	}

	/**
	 * Draws the ship, fuel/speed bars, and background to the provided Canvas.
	 */
	private void doDraw(Canvas g) {
		// Canvas g = gOrig;
		graphicsWrapper = new GraphicsWrapper(g);

		if (waitMessage == false) {
			if (g != null) {
				g.drawColor(Color.argb(255, 10, 10, 10));
				paint(g);
			}
		} else

		{
			g.drawColor(Color.argb(255, 10, 10, 10));
			if (frameWait % 20 < 10)
				graphicsWrapper.drawChars("Please Wait", 0, 11, 5, 5 + graphicsWrapper.getFontHeight(), GraphicsWrapper.COLOR_WHITE, 0);
			else
				graphicsWrapper.drawChars("Please Wait", 0, 11, 5, 5 + graphicsWrapper.getFontHeight(), GraphicsWrapper.COLOR_GREY, 0);
			frameWait++;
			// graphicsWrapper.drawRect(0, 0, 20, 20,
			// GraphicsWrapper.COLOR_WHITE, 255);
		}

	}

	public void paint(Canvas g) {
		frames++;
		frameStartTime = System.currentTimeMillis();
		graphicsWrapper.setG(g);
		if (mouseDown == true) {
			game.mouseMove((int) lastMousex, (int) lastMousey);
		}

		if (game.paint(graphicsWrapper) == true) {
			String temp = game.getActiveScene().tempSavefile;
			if (temp != null) {
				loadGame(temp);
			} else {
				loadGame(Game.SAVE_FILE_NAME);
			}
		}
		frameEndTime = System.currentTimeMillis();
		frameDiffTime = frameEndTime - frameStartTime;
		// frameDiffSum += frameDiffTime;

		// frameAvg = frameDiffSum / frames;
		frameAvg = frameDiffTime;
//		Log.d("avg:" + frameAvg);
		try {
			if (frameGoal - frameAvg > 0) {
				Thread.sleep(frameGoal - frameAvg);
			}

		} catch (InterruptedException ex) {
			// do nothing
		}

	}

	public void restoreGame(String file) {
		Game loadGame;

		try {
			loadGame = Game.loadSerializedState(file);
		} catch (Exception e) {
			Log.w("Savegame corrupted, try to rescue");
			if (!file.equals(Game.SAVE_FILE_NAME)) {
				game.restartScene();
			} else {
				int activeSceneId = Game.loadLastSceneId(Game.INFO_SAVE_FILE_NAME);
				game.getActiveScene().setNewScene(activeSceneId);
			}
			return;
		}

		if (loadGame == null) {
			game.nextScene();
		} else {
			game = loadGame;
			game.restoreSerializedState();
			game.setFrame(this);
		}
	}

	@Override
	public void setFrameGoal(int quality) {
		frameDiffSum = 0;
		frames = 1;
		switch (quality) {
		case Game.QUAL_HIGH:
			frameGoal = frameGoalHigh;
			break;
		case Game.QUAL_MED:
			frameGoal = frameGoalMed;
			break;
		case Game.QUAL_LOW:
			frameGoal = frameGoalLow;
			break;

		default:
			break;
		}
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getGraphicsSizeX() {
		return Game.raster * Scene.DIMX;
	}

	public int getGraphicsSizeY() {
		return Game.raster * Scene.DIMY;
	}

	@Override
	public void saveGame(Game game, String filename, String rfilename) {
		Log.d("Save Game Message");
		IOMessage msgObj = new IOMessage(IOMessage.TYPE_SAVE, game, filename, rfilename);
		Message childMsg = mChildHandler.obtainMessage();
		childMsg.obj = msgObj;
		mChildHandler.sendMessage(childMsg);

	}

	@Override
	public void loadGame(String filename) {
		Log.d("Load Game Message");
		IOMessage msgObj = new IOMessage(IOMessage.TYPE_LOAD, null, filename, null);
		Message childMsg = mChildHandler.obtainMessage();
		childMsg.obj = msgObj;
		mChildHandler.sendMessage(childMsg);
	}

	protected void doDestroy() {
		Log.d("Do destroy");
		mChildHandler.getLooper().quit();
	}

	class ChildThread extends Thread {

		public void run() {
			this.setName("ChildThread");

			// Initialize the message loop in the queue, there is a need for
			// Handler Before you create

			Looper.prepare();

			mChildHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Log.d("Got an incoming message from the main thread");
					IOMessage retMsgObj = null;

					IOMessage msgObj = (IOMessage) msg.obj;
					switch (msgObj.getType()) {
					case IOMessage.TYPE_LOAD:
						Log.d(msgObj.getFilename());
						waitMessage = true;
						retMsgObj = new IOMessage(IOMessage.TYPE_DONE, null, null, null);
						restoreGame(msgObj.getFilename());
						break;
					case IOMessage.TYPE_SAVE:
						waitMessage = true;
						retMsgObj = new IOMessage(IOMessage.TYPE_DONE, null, null, null);
						Game.saveSerializedGame(msgObj.getGame(), msgObj.getFilename(), msgObj.getRfilename());
						break;

					default:
						break;
					}

					Message toMain = mMainHandler.obtainMessage();
					if (retMsgObj != null) {
						toMain.obj = retMsgObj;
					}

					mMainHandler.sendMessage(toMain);

					Log.d("Send a message to the main thread");

				}

			};

			Log.d("Child handler is bound to - " + mChildHandler.getLooper().getThread().getName());

			// Start the child thread message loop queue
			Looper.loop();
		}
	}
	
	public boolean isAndroid() { return true;}

}
