package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.ShiftPlattformComponent;
import com.ad.thepool.components.TransformTileComponent;

public class ShiftPlattformRight extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4089949552665446104L;

	public ShiftPlattformRight(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('>', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 24, true);
		ShiftPlattformComponent shift = new ShiftPlattformComponent(true, Component.RIGHT);
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
