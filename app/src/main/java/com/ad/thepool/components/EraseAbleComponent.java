package com.ad.thepool.components;

public class EraseAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 88210614558358307L;
	public static final int COMP_NAME = 10;

	public EraseAbleComponent(boolean isActive) {
		super(COMP_NAME, isActive);
	}

}
