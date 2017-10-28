package com.ad.thepool.components;

import com.ad.thepool.Scene;

public class CameraComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2963405469245955351L;
	public static final int COMP_NAME = 2;
	public static final int TYPE_FOLLOW_XY = 1;
	public static final int TYPE_FOLLOW_X = 2;
	public static final int TYPE_FOLLOW_Y = 3;
	public static final int TYPE_FIX = 4;

	private int x;
	private int y;
	private int type;

	public CameraComponent(boolean isActive, int type) {
		super(COMP_NAME, isActive);
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setCamera(int x, int y) {
		this.x = x;
		this.y = y;

		Scene scene = gobject.getScene();

		if (type == TYPE_FOLLOW_XY) {
			scene.setCamera(x, y, true, true);
		} else if (type == TYPE_FOLLOW_X) {
			scene.setCamera(x, y, true, false);
		} else if (type == TYPE_FOLLOW_Y) {
			scene.setCamera(x, y, false, true);
		} else if (type == TYPE_FIX) {
			scene.setCamera(x, y, false, false);
		}
	}

}
