package com.ad.thepool.components;

import java.io.Serializable;

public class Pathelement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 53758764066154081L;
	private String tag;
	private int nextOrderId;
	private String message;
	private int duration;
	private boolean stopAfterStep;

	public Pathelement(String tag, int nextOrderId, String message, int duration, boolean stopAfterStep) {
		super();
		this.tag = tag;
		this.nextOrderId = nextOrderId;
		this.message = message;
		this.duration = duration;
		this.stopAfterStep = stopAfterStep;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getNextOrderId() {
		return nextOrderId;
	}

	public void setNextOrderId(int nextOrderId) {
		this.nextOrderId = nextOrderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isStopAfterStep() {
		return stopAfterStep;
	}

	public void setStopAfterStep(boolean stopAfterStep) {
		this.stopAfterStep = stopAfterStep;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
