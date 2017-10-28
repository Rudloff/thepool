package com.ad.thepool.components;

public class DestroyCollideComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1856620601788842123L;

	public static final int COMP_NAME = 8;

	private String ignoreTag;

	private int damageEnergy;
	String key;

	public DestroyCollideComponent(boolean isActive, int damageEnergy, String key, String ignoreTag) {
		super(COMP_NAME, isActive);
		this.damageEnergy = damageEnergy;
		this.key = key;
		this.ignoreTag = ignoreTag;
	}

	public int getDamageEnergy() {
		return damageEnergy;
	}

	public void setDamageEnergy(int damageEnergy) {
		this.damageEnergy = damageEnergy;
	}

	public String getKey() {
		return key;
	}

	public String getIgnoreTag() {
		return ignoreTag;
	}

	public void setIgnoreTag(String ignoreTag) {
		this.ignoreTag = ignoreTag;
	}

}
