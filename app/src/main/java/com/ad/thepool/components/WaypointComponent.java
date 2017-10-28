package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class WaypointComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -633450270574696885L;

	public static final int COMP_NAME = 32;

	private String tag;
	private int order;
	private int visited;
	private String helpEnterText;
	private TextBox help;

	public WaypointComponent(boolean isActive, int order, String tag) {
		super(COMP_NAME, isActive);
		this.order = order;
		this.tag = tag;
		visited = 0;
		helpEnterText = null;
	}

	@Override
	public void onTriggerEnter(GObject collidedObject, int x, int y) {
		int highx = -1, highy = -1;
		if (collidedObject.hasTag(tag)) {
			visited++;
			TransformTileComponent trans = collidedObject.getTransformTileComponent();
			if (trans != null) {
				highx = trans.getX();
				highy = trans.getY();
			}

			if (helpEnterText != null) {
				help = gobject.getScene().addTextBox(highx, highy, 1, 1, 23, 3, helpEnterText, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, true, null, null);
				help.getRenderTextComponent().setTopBox(false);
			}
		}
	}

	@Override
	public void onTriggerLeave(GObject collidedObject, int x, int y) {
		if (collidedObject.hasTag(tag) && help != null) {
			gobject.getScene().removeBufferList(help);
			help = null;
		}
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		if (help != null) {
			gobject.getScene().removeBufferList(help);
		}
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getVisited() {
		return visited;
	}

	public String getHelpEnterText() {
		return helpEnterText;
	}

	public void setHelpEnterText(String helpEnterText) {
		this.helpEnterText = helpEnterText;
	}

}
