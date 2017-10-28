package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BrickRightTop extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2571272799210562810L;

	public BrickRightTop(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('U', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 21, true);
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
