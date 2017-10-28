package com.ad.thepool.components;

import com.ad.thepool.GObject;

public class PushPullAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4954642155268270332L;

	public static final int COMP_NAME = 22;

	private int maxEnergy;
	private int energy;
	private boolean isPush;
	private String switchKey;

	public PushPullAbleComponent(boolean isActive, int maxEnergy, boolean isPush, String switchKey) {
		super(COMP_NAME, isActive);
		this.maxEnergy = maxEnergy;
		this.energy = maxEnergy;
		this.isPush = isPush;
		this.switchKey = switchKey;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean decreaseEnergy(int value) {
		energy -= value;
		if (energy <= 0) {
			energy = maxEnergy;
			return true;
		}
		return false;
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		if (sendkey.equals(switchKey + ".on")) {
			setPush(true);
		}
		if (sendkey.equals(switchKey + ".off")) {
			setPush(false);
		}
	}

	public boolean isPush() {
		return isPush;
	}

	public void setPush(boolean isPush) {
		this.isPush = isPush;
		if (isPush == true) {
			gobject.getRenderTileComponent().setActiveAnimation("push", true);
		} else {
			gobject.getRenderTileComponent().setActiveAnimation("pull", true);
		}

	}

	public String getSwitchKey() {
		return switchKey;
	}

	public void setSwitchKey(String switchKey) {
		this.switchKey = switchKey;
	}

	@Override
	public void destroy() {
		gobject.getScene().removeBufferList(gobject);
	}

}
