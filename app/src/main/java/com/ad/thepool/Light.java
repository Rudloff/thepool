package com.ad.thepool;

public class Light {
	int intensity;
	int color;
	int x;
	int y;

	Light(int intensity, int color, int x, int y) {
		this.intensity = intensity;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
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

}
