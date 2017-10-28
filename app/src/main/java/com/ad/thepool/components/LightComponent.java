package com.ad.thepool.components;

import com.ad.thepool.wrapper.GraphicsWrapper;

public class LightComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8577239390098038575L;

	public static final int COMP_NAME = 40;

	public static final int MODE_NO = 0;
	public static final int MODE_SINUS = 1;
	public static final int MODE_STEP = 2;
	public static final int MODE_SINUS_UP = 3;
	public static final int MODE_RANDOM = 4;
	public static final int MODE_SINUS_DOWN = 5;

	private int intensity;
	private int intensity1;
	private int intensity2;
	private int color;
	private int color1;
	private int color2;
	private int colorSpeed;
	private int colorTimer;
	private int colorMode;
	private int intSpeed;
	private int intTimer;
	private int intMode;
	private float colorStepFactor = 0F;
	private float intStepFactor = 0F;

	public LightComponent(boolean isActive, int intensity1, int intensity2, int ispeed, int imode, int color1, int color2, int cspeed, int cmode) {
		super(COMP_NAME, isActive);
		this.intensity1 = intensity = intensity1;
		this.intensity2 = intensity2;
		this.intSpeed = ispeed;
		this.intMode = imode;
		if (imode == MODE_SINUS_DOWN) {
			intTimer = 90;
			intensity = intensity2;
		} else {
			intTimer = 0;
		}
		this.color1 = color = color1;
		this.color2 = color2;
		this.colorSpeed = cspeed;
		this.colorMode = cmode;
		if (cmode == MODE_SINUS_DOWN) {
			colorTimer = 90;
			color = color2;
		} else {
			colorTimer = 0;
		}
	}

	public LightComponent(boolean isActive, int intensity, int color) {
		super(COMP_NAME, isActive);
		this.intensity = intensity;
		this.intensity2 = -1;
		this.color = color;
		this.color2 = -1;
	}

	@Override
	public void update() {
		// Intensity
		if (intensity2 != -1) {
			if (intMode == MODE_SINUS) {
				intTimer = intTimer + intSpeed;
				intStepFactor = (float) ((Math.sin(Math.toRadians(intTimer)) + 1) * 0.5);
			} else if (intMode == MODE_STEP) {
				intTimer = intTimer + 1;
				if (intTimer % intSpeed == 1) {
					intStepFactor = 0;
				} else if (colorTimer % colorSpeed == colorSpeed / 2) {
					intStepFactor = 1;
				}
			} else if (intMode == MODE_RANDOM) {
				intTimer = intTimer + 1;
				if (intTimer % intSpeed == 0) {
					intStepFactor = (float) Math.random();
				}

			} else if (intMode == MODE_SINUS_UP) {
				if (intTimer < 90) {
					intStepFactor = (float) (Math.sin(Math.toRadians(intTimer)));
					intTimer = intTimer + intSpeed;
				} else {
					intStepFactor = 1;
				}
			} else if (intMode == MODE_SINUS_DOWN) {
				if (intTimer > 0) {
					intStepFactor = (float) (Math.sin(Math.toRadians(intTimer)));
					intTimer = intTimer + intSpeed;
				} else {
					intStepFactor = 0;
				}
			}
			intensity = calcIntensity(intStepFactor);

		}

		// color
		if (color2 != -1) {
			if (colorMode == MODE_SINUS) {
				colorTimer = colorTimer + colorSpeed;
				colorStepFactor = (float) ((Math.sin(Math.toRadians(colorTimer)) + 1) * 0.5);
			} else if (colorMode == MODE_STEP) {
				colorTimer = colorTimer + 1;
				if (colorTimer % colorSpeed == 1) {
					colorStepFactor = 0;
				} else if (colorTimer % colorSpeed == colorSpeed / 2) {
					colorStepFactor = 1;
				}
			} else if (colorMode == MODE_RANDOM) {
				colorTimer = colorTimer + 1;
				if (colorTimer % colorSpeed == 0) {
					colorStepFactor = (float) Math.random();
				}
			} else if (colorMode == MODE_SINUS_UP) {
				if (colorTimer < 90) {
					colorStepFactor = (float) (Math.sin(Math.toRadians(colorTimer)));
					colorTimer = colorTimer + colorSpeed;
				} else {
					colorStepFactor = 1;
				}
			} else if (colorMode == MODE_SINUS_DOWN) {
				if (colorTimer > 0) {
					colorStepFactor = (float) (Math.sin(Math.toRadians(colorTimer)));
					colorTimer = colorTimer + colorSpeed;
				} else {
					colorStepFactor = 0;
				}
			}
			color = GraphicsWrapper.calcColor(color1, color2, colorStepFactor);
		}
	}

	private int calcIntensity(float ratio) {
		if (ratio > 1) {
			return intensity2;
		}
		if (ratio < 0) {
			return intensity1;
		}

		return Math.round((intensity1 * (1 - ratio) + intensity2 * ratio));

	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getIntensity1() {
		return intensity1;
	}

	public void setIntensity1(int intensity1) {
		this.intensity1 = intensity1;
	}

	public int getIntensity2() {
		return intensity2;
	}

	public void setIntensity2(int intensity2) {
		this.intensity2 = intensity2;
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public int getColorSpeed() {
		return colorSpeed;
	}

	public void setColorSpeed(int colorSpeed) {
		this.colorSpeed = colorSpeed;
	}

	public int getColorTimer() {
		return colorTimer;
	}

	public void setColorTimer(int colorTimer) {
		this.colorTimer = colorTimer;
	}

	public int getColorMode() {
		return colorMode;
	}

	public void setColorMode(int colorMode) {
		this.colorMode = colorMode;
	}

	public int getIntSpeed() {
		return intSpeed;
	}

	public void setIntSpeed(int intSpeed) {
		this.intSpeed = intSpeed;
	}

	public int getIntTimer() {
		return intTimer;
	}

	public void setIntTimer(int intTimer) {
		this.intTimer = intTimer;
	}

	public int getIntMode() {
		return intMode;
	}

	public void setIntMode(int intMode) {
		this.intMode = intMode;
	}

}
