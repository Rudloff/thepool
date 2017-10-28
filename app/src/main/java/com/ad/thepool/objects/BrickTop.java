package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BrickTop extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5932703239206421102L;

	public BrickTop(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('O', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 9, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
