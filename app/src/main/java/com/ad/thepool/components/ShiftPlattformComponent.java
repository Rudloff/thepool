package com.ad.thepool.components;

public class ShiftPlattformComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3098027269189710847L;

	public static final int COMP_NAME = 27;

	private int direction;

	public ShiftPlattformComponent(boolean isActive, int direction) {
		super(COMP_NAME, isActive);
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
