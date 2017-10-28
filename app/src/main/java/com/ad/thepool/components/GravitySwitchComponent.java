package com.ad.thepool.components;

public class GravitySwitchComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3143601145951487554L;

	public static final int COMP_NAME = 12;

	private int toGrav;

	public GravitySwitchComponent(boolean isActive, int toGrav) {
		super(COMP_NAME, isActive);
		this.toGrav = toGrav;
	}

	public int getToGrav() {
		return toGrav;
	}

	public void setToGrav(int toGrav) {
		this.toGrav = toGrav;
	}

}
