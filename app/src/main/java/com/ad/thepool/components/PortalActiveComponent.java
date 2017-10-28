package com.ad.thepool.components;

public class PortalActiveComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4035152515395045508L;
	public static final int COMP_NAME = 20;

	public PortalActiveComponent(boolean isActive) {
		super(COMP_NAME, isActive);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
