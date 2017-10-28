package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.TextBox;

public class DoorComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4009292726175844511L;

	public static final int COMP_NAME = 9;

	private boolean isOpen;
	private String key;
	private TextBox help;
	private boolean reverseSwitch;

	public DoorComponent(boolean isActive, String key) {
		super(COMP_NAME, isActive);
		this.isOpen = false;
		this.key = key;
		reverseSwitch = false;
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
		CollideAbleComponent coll = gobject.getCollideAbleComponent();
		TransformTileComponent trans = gobject.getTransformTileComponent();

		if (render != null && coll != null && trans != null) {
			if (isOpen == true) {
				render.setActiveAnimation("open", true);
				if (gobject.getScene() != null) {
					getSound("open").play();
				}
				coll.setActive(false);
				trans.setZ(GObject.Z_BACK, false);
			} else {
				render.setActiveAnimation("close", true);
				if (gobject.getScene() != null) {
					getSound("close").play();
				}
				coll.setActive(true);
				trans.setZ(GObject.Z_MAIN, false);
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

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		if (reverseSwitch == false) {
			if (sendkey.equals(key + ".on")) {
				setOpen(true);
			}
			if (sendkey.equals(key + ".off")) {
				setOpen(false);
			}
		} else {
			if (sendkey.equals(key + ".on")) {
				setOpen(false);
			}
			if (sendkey.equals(key + ".off")) {
				setOpen(true);
			}

		}
	}

	public boolean isReverseSwitch() {
		return reverseSwitch;
	}

	public void setReverseSwitch(boolean reverseSwitch) {
		this.reverseSwitch = reverseSwitch;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
