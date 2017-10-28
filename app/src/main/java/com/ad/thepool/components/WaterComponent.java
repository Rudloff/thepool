package com.ad.thepool.components;

public class WaterComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5448307308559492555L;

	public static final int COMP_NAME = 36;

	private int floaterDir;

	public WaterComponent(boolean isActive, int floaterDir) {
		super(COMP_NAME, isActive);
		this.floaterDir = floaterDir;
	}

	public int getFloaterDir() {
		return floaterDir;
	}

	public void setFloaterDir(int floaterDir) {
		this.floaterDir = floaterDir;
	}

}
