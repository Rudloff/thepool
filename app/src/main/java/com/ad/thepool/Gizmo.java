package com.ad.thepool;

import java.io.Serializable;

public class Gizmo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8277776442689428865L;
	private int x;
	private int y;
	private char c;

	public Gizmo(int x, int y, char c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}
}
