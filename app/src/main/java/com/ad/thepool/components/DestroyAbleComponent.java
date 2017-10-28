package com.ad.thepool.components;

import com.ad.thepool.GObject;

public class DestroyAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8858131167000771736L;

	public static final int COMP_NAME = 7;

	private int maxEnergy;
	private int energy;
	private int showEnergy;
	private int regenerate;
	private int timeShowEnergy;
	private int timeRegenerate;
	private boolean doRegerate;
	public String destroyKey;

	public DestroyAbleComponent(boolean isActive, int maxEnergy, boolean doRegerate, int timeRegenerate, String destroyKey) {
		super(COMP_NAME, isActive);
		this.maxEnergy = maxEnergy;
		this.energy = maxEnergy;
		showEnergy = 0;
		timeShowEnergy = 20;
		this.timeRegenerate = timeRegenerate;
		this.doRegerate = doRegerate;
		this.destroyKey = destroyKey;
	}

	@Override
	public void update() {
		if (showEnergy > 0) {
			showEnergy -= 1;
		}
		if (doRegerate == true) {
			if (regenerate > 0) {
				regenerate -= 1;
				if (regenerate <= 0) {
					energy = maxEnergy;
					showEnergy = timeShowEnergy;
				}
			}
		}
		TransformTileComponent trans = gobject.getTransformTileComponent();
		GObject frontObj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_FRONT);
		GObject backObj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK);

		if (frontObj != null && frontObj.getDestroyCollideComponent() != null) {
			damageCollide(frontObj);
			getSound("collide").play();
		} else if (backObj != null && backObj.getDestroyCollideComponent() != null) {
			damageCollide(backObj);
			getSound("collide").play();

		}

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
		showEnergy = timeShowEnergy;
		regenerate = timeRegenerate;
		energy -= value;
		if (energy <= 0) {
			energy = 0;
			return true;
		}
		return false;
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		energy = maxEnergy;
		showEnergy = timeShowEnergy;
	}

	@Override
	public void destroy() {
		gobject.getScene().removeBufferList(gobject);
	}

	public int getShowEnergy() {
		return showEnergy;
	}

	public int getTimeShowEnergy() {
		return timeShowEnergy;
	}

}
