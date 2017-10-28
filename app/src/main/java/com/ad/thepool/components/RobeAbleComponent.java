package com.ad.thepool.components;

public class RobeAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7075493423012813035L;
	public static final int COMP_NAME = 26;
	public int forGravity;

	public RobeAbleComponent(boolean isActive, int forGravity) {
		super(COMP_NAME, isActive);
		this.forGravity = forGravity;
	}

	public int getForGravity() {
		return forGravity;
	}

	public void setForGravity(int forGravity) {
		this.forGravity = forGravity;
	}

}
