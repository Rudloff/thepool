package com.ad.thepool.components;

public class WeightComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5427985740408052253L;
	public static final int COMP_NAME = 33;

	public WeightComponent(boolean isActive) {
		super(COMP_NAME, isActive);
	}

}
