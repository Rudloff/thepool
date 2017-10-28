package com.ad.thepool.components;

public class ClimbAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7545336508618976655L;
	public static final int COMP_NAME = 3;

	public ClimbAbleComponent(boolean isActive, boolean forDisable) {
		super(COMP_NAME, isActive);
	}

}
