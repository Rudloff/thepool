package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.ShiftPlattformComponent;
import com.ad.thepool.components.TransformTileComponent;

public class ShiftPlattformUp extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3226008149432553942L;

	public ShiftPlattformUp(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('^', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 26, true);
		ShiftPlattformComponent shift = new ShiftPlattformComponent(true, Component.UP);
		CollideAbleComponent collide = new CollideAbleComponent(true);
		// TODO: Turn
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(shift);
		addComponent(collide);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
