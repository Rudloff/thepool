package com.ad.thepool;

public class ControlEvent {

	public static final int TYPE_KEY_DOWN = 1;
	public static final int TYPE_KEY_UP = 2;
	public static final int TYPE_MOUSE_DOWN = 3;	
	public static final int TYPE_MOUSE_UP = 4;	
	public static final int TYPE_MOUSE_MOVE = 5;	
	
	int type;
	int key;
	int mouseX;
	int mouseY;
	
	public ControlEvent(int type, int key) 
	{
		this.type = type;
		this.key = key;
	}

	public ControlEvent(int type, int mouseX, int mouseY) 
	{
		this.type = type;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getMouseX() {
		return mouseX;
	}
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
}
