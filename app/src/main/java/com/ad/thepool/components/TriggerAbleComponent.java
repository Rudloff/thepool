package com.ad.thepool.components;

public class TriggerAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7982869708623596805L;
	public static final int COMP_NAME = 30;

	public TriggerAbleComponent(boolean isActive) {
		super(COMP_NAME, isActive);
	}

}
