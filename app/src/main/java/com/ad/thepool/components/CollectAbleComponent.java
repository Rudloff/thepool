package com.ad.thepool.components;

public class CollectAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8620261399736730314L;

	public static final int COMP_NAME = 34;

	private String counterKey;

	public CollectAbleComponent(boolean isActive, String counterKey) {
		super(COMP_NAME, isActive);
		this.counterKey = counterKey;
	}

	public String getCounterKey() {
		return counterKey;
	}

	public void setCounterKey(String counterKey) {
		this.counterKey = counterKey;
	}

}
