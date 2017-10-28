package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.components.MenuComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Menu extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2614381663541385373L;

	public Menu(int id) {
		super(id, true);
		TransformTileComponent trans = new TransformTileComponent('$', true, GObject.Z_FOREGROUND);
		MenuComponent menu = new MenuComponent(true);
		addComponent(trans);
		addComponent(menu);

		trans.setXY(Scene.DIMX - 2, Scene.DIMY - 1, false);

		getTagList().add("menu");
	}

}
