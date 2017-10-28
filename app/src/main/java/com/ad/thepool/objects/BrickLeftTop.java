package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BrickLeftTop extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 684780477557760597L;

	public BrickLeftTop(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('Y', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 10, true);
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
