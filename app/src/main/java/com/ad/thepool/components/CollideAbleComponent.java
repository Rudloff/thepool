package com.ad.thepool.components;

public class CollideAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5292830010576881838L;
	public static final int COMP_NAME = 4;

	public CollideAbleComponent(boolean isActive) {
		super(COMP_NAME, isActive);
	}

}
