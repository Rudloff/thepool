package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.components.MouseCursorComponent;
import com.ad.thepool.components.TransformTileComponent;

public class MouseCursor extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -922134570434025534L;

	public MouseCursor(int id) {
		super(id, true);
		TransformTileComponent trans = new TransformTileComponent('Z', true, GObject.Z_FOREGROUND);
		MouseCursorComponent mouseCursor = new MouseCursorComponent(true);
		addComponent(trans);
		addComponent(mouseCursor);

		trans.setXY(0, 0, false);

		getTagList().add("cursor");
	}

}
