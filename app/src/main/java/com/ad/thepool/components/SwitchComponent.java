package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SwitchComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8992495556564905138L;
	public static final int COMP_NAME = 28;
	public static final int SWITCH_ON = 0;
	public static final int SWITCH_OFF = 1;

	public static final int TYPE_MANUAL = 1;
	public static final int TYPE_HEAVYW = 2;
	public static final int TYPE_TIMED = 3;
	public static final int TYPE_ONCE = 4;
	public static final int TYPE_HEAVYW_TIMED = 5;

	public static final int MAX_HELP_DURATION = 20;

	public static final int GREEN1 = GraphicsWrapper.getRGB(0, 64, 0, 0);
	public static final int GREEN2 = GraphicsWrapper.getRGB(0, 140, 0, 0);
	public static final int RED1 = GraphicsWrapper.getRGB(64, 0, 0, 0);
	public static final int RED2 = GraphicsWrapper.getRGB(140, 0, 0, 0);

	private int switchState;
	private String key;
	private TextBox help;
	private boolean pressAction;
	private boolean allowSwitch;
	private int type;
	private boolean isInit;
	private int helpDuration;
	private int timer;
	private int duration;
	private GObject lastCollidedObject;

	private String infoOn, infoOff, actOn, actOff, onInit;

	public SwitchComponent(boolean isActive, int switchState, String key, int type, String infoOn, String infoOff, String actOn, String actOff, String onInit) {
		super(COMP_NAME, isActive);
		setSwitchState(switchState);
		this.key = key;
		this.type = type;
		help = null;
		pressAction = false;
		allowSwitch = true;
		this.infoOn = infoOn;
		this.infoOff = infoOff;
		this.actOn = actOn;
		this.actOff = actOff;
		this.onInit = onInit;
		isInit = false;
		helpDuration = 0;
		duration = 0;
		timer = 0;
		lastCollidedObject = null;
	}

	public SwitchComponent(boolean isActive, int switchState, String key, int type, String infoOn, String infoOff, String actOn, String actOff, String onInit, int duration) {
		super(COMP_NAME, isActive);
		setSwitchState(switchState);
		this.key = key;
		this.type = type;
		help = null;
		pressAction = false;
		allowSwitch = true;
		this.infoOn = infoOn;
		this.infoOff = infoOff;
		this.actOn = actOn;
		this.actOff = actOff;
		this.onInit = onInit;
		isInit = false;
		helpDuration = 0;
		this.duration = duration;
		timer = 0;
		lastCollidedObject = null;
	}

	public int getSwitchState() {
		return switchState;
	}

	public void setSwitchState(int switchState) {
		setSwitchState(switchState, "on");
	}

	public void setSwitchState(int switchState, String animOn) {
		this.switchState = switchState;
		if (gobject == null) {
			return;
		}

		RenderTileComponent render = gobject.getRenderTileComponent();
		LightComponent light = gobject.getLightComponent();

		if (render != null) {
			if (switchState == SWITCH_ON) {
				render.setActiveAnimation(animOn, true);
				if (light != null) {
					light.setColor1(GREEN1);
					light.setColor2(GREEN2);

				}
				getSound("switch_on").play();

			} else {
				render.setActiveAnimation("off", true);
				if (light != null) {
					light.setColor1(RED1);
					light.setColor2(RED2);

				}
				getSound("switch_off").play();
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if (timer > 0) {
			timer--;
			if (timer == 0) {
				setSwitchState(SWITCH_OFF);
				gobject.getScene().sendMessage(gobject, lastCollidedObject, null, key + ".off");
				lastCollidedObject = null;
			}
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void keyDown(int key) {
		if (key == KeyEventWrapper.KEY_ENTER) {
			pressAction = true;
		}
	}

	@Override
	public void keyUp(int key) {
		if (key == KeyEventWrapper.KEY_ENTER) {
			pressAction = false;
			allowSwitch = true;
		}
	}

	@Override
	public void onTriggerEnter(GObject collidedObject, int x, int y) {
		if (type == TYPE_MANUAL) {
			if (collidedObject.hasTag("player")) {
				TransformTileComponent trans = collidedObject.getTransformTileComponent();
				int highx = trans.getX();
				int highy = trans.getY();

				if (switchState == SWITCH_OFF) {
					help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, infoOn, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				} else {
					help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, infoOff, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				}
			}
		} else if (type == TYPE_HEAVYW) {
			WeightComponent weight = collidedObject.getWeightComponent();
			if (weight != null) {
				if (switchState == SWITCH_OFF) {
					setSwitchState(SWITCH_ON);
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".on");
				} else {
					setSwitchState(SWITCH_OFF);
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".off");
				}
			}
		} else if (type == TYPE_ONCE) {
			if (collidedObject.hasTag("player")) {
				TransformTileComponent trans = collidedObject.getTransformTileComponent();
				int highx = trans.getX();
				int highy = trans.getY();

				if (switchState == SWITCH_OFF) {
					help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, infoOn, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				}
			}
		} else if (type == TYPE_HEAVYW_TIMED) {
			WeightComponent weight = collidedObject.getWeightComponent();
			if (weight != null) {
				if (switchState == SWITCH_OFF) {
					setSwitchState(SWITCH_ON, "blink");
					timer = duration;
					lastCollidedObject = collidedObject;
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".on");
				}
			}
		}

	}

	@Override
	public void onTriggerStay(GObject collidedObject, int x, int y) {
		if (helpDuration > 0) {
			helpDuration--;
			if (helpDuration <= 0) {
				helpDuration = 0;
				if (help != null) {
					gobject.getScene().removeBufferList(help);
					help = null;
				}
			}
		}
		if (type == TYPE_MANUAL) {
			if (collidedObject.hasTag("player") && pressAction == true && allowSwitch == true) {
				if (help == null) {
					TransformTileComponent trans = collidedObject.getTransformTileComponent();
					int highx = trans.getX();
					int highy = trans.getY();
					help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, infoOn, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				}
				RenderTextComponent rendertext = help.getRenderTextComponent();

				if (switchState == SWITCH_ON) {
					rendertext.setText(actOff);
					setSwitchState(SWITCH_OFF);
					helpDuration = MAX_HELP_DURATION;
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".off");
				} else {
					rendertext.setText(actOn);
					setSwitchState(SWITCH_ON);
					helpDuration = MAX_HELP_DURATION;
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".on");
				}
				allowSwitch = false;
			}

		} else if (type == TYPE_ONCE) {
			if (collidedObject.hasTag("player") && pressAction == true && allowSwitch == true) {
				if (help == null) {
					TransformTileComponent trans = collidedObject.getTransformTileComponent();
					int highx = trans.getX();
					int highy = trans.getY();
					help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, infoOn, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				}

				RenderTextComponent rendertext = help.getRenderTextComponent();

				if (switchState == SWITCH_OFF) {
					rendertext.setText(actOn);
					setSwitchState(SWITCH_OFF);
					helpDuration = MAX_HELP_DURATION;
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".on");
				}
				allowSwitch = false;
			}
			if (isInit == true) {
				if (help != null) {
					gobject.getScene().removeBufferList(help);
					help = null;
				}
				TransformTileComponent trans = collidedObject.getTransformTileComponent();
				int highx = trans.getX();
				int highy = trans.getY();

				help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, onInit, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				isInit = false;
			}
		}

	}

	@Override
	public void onTriggerLeave(GObject collidedObject, int x, int y) {
		if (type == TYPE_MANUAL || type == TYPE_ONCE) {
			if (collidedObject.hasTag("player") && help != null) {
				gobject.getScene().removeBufferList(help);
				help = null;
			}
		} else if (type == TYPE_HEAVYW) {
			WeightComponent weight = collidedObject.getWeightComponent();
			if (weight != null) {
				if (switchState == SWITCH_OFF) {
					setSwitchState(SWITCH_ON);
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".on");
				} else {
					setSwitchState(SWITCH_OFF);
					gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".off");
				}
			}
		}
	}

	@Override
	public void initRestore() {
		super.initRestore();
		if (help != null) {
			gobject.getScene().removeBufferList(help);
		}
		pressAction = false;
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		// TODO Auto-generated method stub
		super.receiveMessage(source, dest, tag, sendkey);
		if (sendkey.equals(key + ".restore")) {
			isInit = true;
		}
		if (sendkey.equals("mouse.click.enter")) {
			pressAction = true;
		}
		if (tag != null && gobject.hasTag(tag)) {
			if (sendkey.equals("enter.down")) {
				pressAction = true;
			}
			if (sendkey.equals("enter.up")) {
				pressAction = false;
				allowSwitch = true;

			}
		}

	}

	@Override
	public boolean mouseUp(int x, int y) {
		pressAction = false;
		allowSwitch = true;
		return false;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getInfoOn() {
		return infoOn;
	}

	public void setInfoOn(String infoOn) {
		this.infoOn = infoOn;
	}

	public String getInfoOff() {
		return infoOff;
	}

	public void setInfoOff(String infoOff) {
		this.infoOff = infoOff;
	}

	public String getActOn() {
		return actOn;
	}

	public void setActOn(String actOn) {
		this.actOn = actOn;
	}

	public String getActOff() {
		return actOff;
	}

	public void setActOff(String actOff) {
		this.actOff = actOff;
	}

	public boolean isAllowSwitch() {
		return allowSwitch;
	}

	public int getType() {
		return type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
