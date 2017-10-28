package com.ad.thepool.components;

import java.io.Serializable;

public class Animation implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6435180056537495661L;
	private int tileStart;
	private int tileEnd;
	private int speed;
	private int type;
	private boolean isActive;
	private boolean pingpong;
	private double frameAnim;

	public static final int TYPE_LOOP = 0;
	public static final int TYPE_BACKLOOP = 1;
	public static final int TYPE_SEQ = 2;
	public static final int TYPE_BACKSEQ = 3;
	public static final int TYPE_PINGPONG = 4;
	public static final int TYPE_STILL = 5;

	public Animation(int tileStart, int tileEnd, int speed, int type, boolean isActive) {
		super();
		this.tileStart = tileStart;
		this.tileEnd = tileEnd;
		this.speed = speed;
		this.type = type;
		this.isActive = isActive;
		frameAnim = 0;
		pingpong = true;
	}

	public int getAnimLength() {
		return (tileEnd - tileStart) + 1;
	}

	public int getTilePos(int frame) {
		int stepElement = (int) (frameAnim % getAnimLength());
		frameAnim += (double) 1 / speed;

		if (isActive == false) {
			return -1;
		}

		if (type == TYPE_LOOP) {
			return tileStart + stepElement;
		} else if (type == TYPE_BACKLOOP) {
			return tileEnd - stepElement;
		} else if (type == TYPE_SEQ) {
			if (stepElement == getAnimLength() - 1) {
				isActive = false;
			}
			return tileStart + stepElement;
		} else if (type == TYPE_BACKSEQ) {
			if (stepElement == getAnimLength() - 1) {
				isActive = false;
			}
			return tileEnd - stepElement;
		} else if (type == TYPE_PINGPONG) {
			int tile;

			if (pingpong == true) {
				tile = tileStart + stepElement;
			} else {
				tile = tileEnd - stepElement;
			}

			if (stepElement == 0 && pingpong == false) {
				pingpong = true;
			} else if (stepElement == 0 && pingpong == true) {
				pingpong = false;
			}
			return tile;
		} else if (type == TYPE_STILL) {
			return tileStart;
		} else {
			return -1;
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
