package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.TransformTileComponent;

public class TextBox extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -880308431489053168L;

	public TextBox(int id, TileMap tilemap, int textFrameTileStart) {
		super(id, true);
		TransformTileComponent trans = new TransformTileComponent('T', true, GObject.Z_FRONT);
		RenderTextComponent rendertext = new RenderTextComponent(true, textFrameTileStart);
		addComponent(trans);
		addComponent(rendertext);

		trans.setXY(2, 0, false);

		getTagList().add("button");

	}

}
