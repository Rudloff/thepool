package com.ad.thepool.components;

public class LaserTransportAbleComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4875055088338993878L;

	public static final int COMP_NAME = 14;

	private int timer;
	private boolean once;
	private boolean doneStep;

	public static final int MAX_TIMER = 5;

	public LaserTransportAbleComponent(boolean isActive, boolean once) {
		super(COMP_NAME, isActive);
		this.once = once;
	}

	@Override
	public void update() {
		if (timer > 0) {
			timer--;
		}
	}

	public void resetTimer() {
		timer = MAX_TIMER;
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		resetTimer();
	}

	public boolean isTimerFinish() {
		if (timer <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOnce() {
		return once;
	}

	public void setOnce(boolean once) {
		this.once = once;
	}

	public boolean isDoneStep() {
		if (once == true) {
			return doneStep;
		} else {
			return false;
		}
	}

	public void setDoneStep() {
		if (once == true) {
			this.doneStep = true;
			RenderTileComponent render = gobject.getRenderTileComponent();
			render.setActiveAnimation("off", true);
		}
	}

}
