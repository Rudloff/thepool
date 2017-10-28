package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.ShiftPlattformComponent;
import com.ad.thepool.components.TransformTileComponent;

public class ShiftPlattformDown extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4688610154158431443L;

	public ShiftPlattformDown(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('y', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 28, true);
		ShiftPlattformComponent shift = new ShiftPlattformComponent(true, Component.DOWN);
		CollideAbleComponent collide = new CollideAbleComponent(true);
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
