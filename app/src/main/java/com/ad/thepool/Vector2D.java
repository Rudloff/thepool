package com.ad.thepool;

import java.io.Serializable;

public class Vector2D implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7652419775116556974L;
	private double x;
	private double y;
	private double normX;
	private double normY;
	private double len;
	private double rot;

	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		calcNorm();
	}

	public Vector2D() {
		super();
		this.x = 0;
		this.y = 0;
		calcNorm();
	}

	public void reverse() {
		x = x * -1;
		y = y * -1;
		calcNorm();
	}

	private void calcNorm() {
		len = Math.sqrt((x * x) + (y * y));

		normX = x / len;
		normY = y / len;
	}

	public void setVector(double x, double y) {
		this.x = x;
		this.y = y;
		calcNorm();
	}

	public static Vector2D add(Vector2D vec1, Vector2D vec2) {
		Vector2D resVec = new Vector2D();
		resVec.setVector(vec1.x + vec2.x, vec1.y + vec2.y);
		return resVec;
	}

	public static Vector2D sub(Vector2D vec1, Vector2D vec2) {
		Vector2D resVec = new Vector2D();
		resVec.setVector(vec1.x - vec2.x, vec1.y - vec2.y);
		return resVec;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getNormX() {
		return normX;
	}

	public double getNormY() {
		return normY;
	}

	public void setLen(double len) {
		double xd = normX * len;
		double yd = normY * len;

		setVector(xd, yd);

	}

	public double getLen() {
		return len;
	}

	public double getRot() {
		rot = Math.atan2(y, x);

		return rot;
	}

	public void rotate(double angle) {
		if (angle != 0) {
			double xd = x * Math.cos(angle) - y * Math.sin(angle);
			double yd = x * Math.sin(angle) + y * Math.cos(angle);

			setVector(xd, yd);
		}
	}

	@Override
	public String toString() {
		String res = new String();

		res = "x: " + x;
		res += " y: " + y;
		res += " normX:" + normX;
		res += " normY:" + normY;
		res += " len:" + len;
		res += " rot:" + getRot();
		res += " rotDeg:" + Math.toDegrees(rot);

		return res;
	}

	public static void main(String[] args) {
		Vector2D vec1 = new Vector2D(10, 10);
		Vector2D vec2 = new Vector2D(40, 10);

		System.out.println(vec1);
		System.out.println(vec2);
		System.out.println(Vector2D.sub(vec1, vec2));

	}

}
