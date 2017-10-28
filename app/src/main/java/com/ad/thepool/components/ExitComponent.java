package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ExitComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7798689283007793323L;

	public static final int COMP_NAME = 11;

	private int nextSceneId;
	private boolean isOpen;
	private String key;
	private boolean pressAction;
	private TextBox help;
	private String helpEnterText;

	public ExitComponent(boolean isActive, String key, boolean isOpen) {
		super(COMP_NAME, isActive);
		nextSceneId = -1;
		setOpen(isOpen);
		this.key = key;
		helpEnterText = "Enter";
	}

	public int getNextSceneId() {
		return nextSceneId;
	}

	public void setNextSceneId(int nextSceneId) {
		this.nextSceneId = nextSceneId;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;

		if (gobject == null) {
			return;
		}
		RenderTileComponent render = gobject.getRenderTileComponent();

		if (render != null) {
			if (isOpen == true) {
				render.setActiveAnimation("open", true);
				getSound("open").play();
			} else {
				render.setActiveAnimation("close", true);
				getSound("close").play();
			}
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public TextBox getHelp() {
		return help;
	}

	public void setHelp(TextBox help) {
		this.help = help;
	}

	public boolean isPressAction() {
		return pressAction;
	}

	@Override
	public void onTriggerEnter(GObject collidedObject, int x, int y) {
		if (collidedObject.hasTag("player")) {
			TransformTileComponent trans = collidedObject.getTransformTileComponent();
			int highx = trans.getX();
			int highy = trans.getY();

			if (isOpen == true) {
				help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, helpEnterText, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
			} else {
				help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 2, "Door Closed", GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
			}
		}
	}

	@Override
	public void onTriggerStay(GObject collidedObject, int x, int y) {
		if (collidedObject.hasTag("player")) {
			if (pressAction == true && isOpen == true) {
				gobject.getScene().sendMessage(gobject, collidedObject, null, key + ".enter");
			}
		}
	}

	@Override
	public void onTriggerLeave(GObject collidedObject, int x, int y) {
		if (collidedObject.hasTag("player") && help != null) {
			gobject.getScene().removeBufferList(help);
			help = null;
		}
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
		}
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		pressAction = false;
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		if (sendkey.equals(key + ".on")) {
			setOpen(true);
		}
		if (sendkey.equals(key + ".off")) {
			setOpen(false);
		}
		if (sendkey.equals("mouse.click.enter")) {
			pressAction = true;
		}
	}

	@Override
	public boolean mouseUp(int x, int y) {
		pressAction = false;
		return true;
	}

	public String getHelpEnterText() {
		return helpEnterText;
	}

	public void setHelpEnterText(String helpEnterText) {
		this.helpEnterText = helpEnterText;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
