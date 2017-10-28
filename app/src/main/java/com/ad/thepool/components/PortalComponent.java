package com.ad.thepool.components;

public class PortalComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9066609874919598455L;

	public static final int COMP_NAME = 21;

	private int order;
	private int fireExitDir;

	public PortalComponent(boolean isActive, int order) {
		super(COMP_NAME, isActive);
		this.order = order;
		this.fireExitDir = UNKNOWN;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getFireExitDir() {
		return fireExitDir;
	}

	public void setFireExitDir(int fireExitDir) {
		this.fireExitDir = fireExitDir;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
