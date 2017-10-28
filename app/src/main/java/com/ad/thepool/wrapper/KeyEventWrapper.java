package com.ad.thepool.wrapper;

import android.view.KeyEvent;


public class KeyEventWrapper {
	public static final int KEY_DOWN = 0;
	public static final int KEY_UP = 1;
	public static final int KEY_LEFT = 2;
	public static final int KEY_RIGHT = 3;

	public static final int KEY_LASER_DOWN = 4;
	public static final int KEY_LASER_UP = 5;
	public static final int KEY_LASER_LEFT = 6;
	public static final int KEY_LASER_RIGHT = 7;

	public static final int KEY_ENTER = 8;
	public static final int KEY_RESTART = 9;
	public static final int KEY_CHECKPOINT = 10;
	public static final int KEY_PAUSE = 11;
	public static final int KEY_MAIN = 12;


	public static int convertKey(int key) {
		int ret = -1;

		switch (key) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			ret = KEY_DOWN;
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			ret = KEY_UP;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			ret = KEY_LEFT;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			ret = KEY_RIGHT;
			break;
		case KeyEvent.KEYCODE_S:
			ret = KEY_LASER_DOWN;
			break;
		case KeyEvent.KEYCODE_W:
			ret = KEY_LASER_UP;
			break;
		case KeyEvent.KEYCODE_A:
			ret = KEY_LASER_LEFT;
			break;
		case KeyEvent.KEYCODE_D:
			ret = KEY_LASER_RIGHT;
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			ret = KEY_ENTER;
			break;
		case KeyEvent.KEYCODE_ENTER:
			ret = KEY_ENTER;
			break;
		case KeyEvent.KEYCODE_N:
			ret = KEY_RESTART;
			break;
		case KeyEvent.KEYCODE_R:
			ret = KEY_CHECKPOINT;
			break;
		case KeyEvent.KEYCODE_P:
			ret = KEY_PAUSE;
			break;
		case KeyEvent.KEYCODE_M:
			ret = KEY_MAIN;
			break;
		default:
			break;
		}
		return ret;

	}

}
